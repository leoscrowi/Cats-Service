package is.tech.cats.services;

import is.tech.cats.contracts.CatService;
import is.tech.cats.daos.CatEntityRepository;
import is.tech.cats.domain.entities.CatEntity;
import is.tech.cats.domain.entities.Color;
import is.tech.cats.domain.mappers.CatMapper;
import is.tech.dtos.CatEntityDto;
import is.tech.dtos.GetCatsByOwnerIdDto;
import is.tech.dtos.GetCatsPageDto;
import is.tech.dtos.PageDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static is.tech.cats.rabbitmq.CatsRabbitMQConfig.*;

@Service
@AllArgsConstructor
public class CatServiceImpl implements CatService {

    private final CatEntityRepository catEntityRepository;

    @RabbitListener(queues = GET_BY_ID_QUEUE)
    @Override
    public CatEntityDto getCatById(long catId) {
        return CatMapper.toDto(catEntityRepository.getCatEntityByCatId(catId));
    }

    @RabbitListener(queues = GET_ALL_QUEUE)
    @Override
    public PageDto<CatEntityDto> getAllCats(GetCatsPageDto catPageDto) {
        Pageable paging = PageRequest.of(catPageDto.getPage(), catPageDto.getSize());

        Color color = null;
        if (catPageDto.getColor() != null) {
            color = Color.valueOf(catPageDto.getColor());
        }

        return new PageDto<>(catEntityRepository.getCatEntitiesByFilter(paging, color, catPageDto.getBreed()).map(CatMapper::toDto));
    }

    @RabbitListener(queues = CREATE_QUEUE)
    @Override
    public CatEntityDto createCat(CatEntityDto cat) {
        return CatMapper.toDto(catEntityRepository.save(CatMapper.toEntity(cat)));
    }

    @RabbitListener(queues = DELETE_BY_ID_QUEUE)
    @Override
    public void deleteCatById(long catId) {
        catEntityRepository.deleteById(catId);
    }

    @RabbitListener(queues = DELETE_ALL_QUEUE)
    @Override
    public void deleteAllCats() {
        catEntityRepository.deleteAll();
    }

    @RabbitListener(queues = GET_BY_OWNER_ID_QUEUE)
    @Override
    public PageDto<CatEntityDto> getCatsByOwnerId(GetCatsByOwnerIdDto dto) {
        Pageable paging = PageRequest.of(dto.getPage(), dto.getSize());

        return new PageDto<>(catEntityRepository.getCatEntitiesByOwnerId(dto.getOwnerId(), paging).map(CatMapper::toDto));
    }

    @Override
    public List<CatEntityDto> getCatsByOwnerId(long ownerId) {
        List<CatEntity> cats = catEntityRepository.getCatEntitiesByOwnerId(ownerId);
        return cats.stream().map(CatMapper::toDto).toList();
    }
}
