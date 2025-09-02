package is.tech.cats.services;

import is.tech.cats.contracts.CatFriendshipService;
import is.tech.cats.daos.CatFriendshipRepository;
import is.tech.cats.domain.mappers.FriendshipMapper;
import is.tech.dtos.FriendEntityDto;
import is.tech.dtos.GetCatsFriendsDto;
import is.tech.dtos.PageDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static is.tech.cats.rabbitmq.CatsRabbitMQConfig.*;

@Service
@AllArgsConstructor
public class CatFriendshipServiceImpl implements CatFriendshipService {

    private final CatFriendshipRepository catFriendRepository;

    @RabbitListener(queues = GET_ALL_FRIENDSHIPS_QUEUE)
    @Override
    public PageDto<FriendEntityDto> getCatsFriends(GetCatsFriendsDto dto) {
        List<FriendEntityDto> result = new ArrayList<>();
        Pageable paging = PageRequest.of(dto.getPage(), dto.getSize());
        result.addAll(catFriendRepository.getAllByFirstCatId(dto.getCatId()).stream().map(FriendshipMapper::toDto).collect(Collectors.toCollection(ArrayList::new)));
        result.addAll(catFriendRepository.getAllBySecondCatId(dto.getCatId()).stream().map(FriendshipMapper::toDto).collect(Collectors.toCollection(ArrayList::new)));
        return new PageDto<>(new PageImpl<>(result, paging, result.size()));
    }

    @RabbitListener(queues = DELETE_FRIENDS_QUEUE)
    @Override
    public void deleteCatFriendship(long friendshipId) {
        catFriendRepository.deleteById(friendshipId);
    }

    @RabbitListener(queues = CREATE_FRIENDS_QUEUE)
    @Override
    public FriendEntityDto createCatFriendship(FriendEntityDto catFriendship) {
        List<FriendEntityDto> first = catFriendRepository.getAllByFirstCatId(catFriendship.getFirstCatId()).stream().map(FriendshipMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
        List<FriendEntityDto> second = catFriendRepository.getAllBySecondCatId(catFriendship.getFirstCatId()).stream().map(FriendshipMapper::toDto).collect(Collectors.toCollection(ArrayList::new));

        for (FriendEntityDto catFriend : first) {
            if (catFriend.getSecondCatId() == catFriendship.getSecondCatId()) {
                return null;
            }
        }

        for (FriendEntityDto catFriend : second) {
            if (catFriend.getFirstCatId() == catFriendship.getFirstCatId()) {
                return null;
            }
        }

        return FriendshipMapper.toDto(catFriendRepository.save(FriendshipMapper.toEntity(catFriendship)));
    }
}
