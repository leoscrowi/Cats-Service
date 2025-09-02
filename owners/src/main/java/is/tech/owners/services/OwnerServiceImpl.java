package is.tech.owners.services;

import is.tech.dtos.GetAllOwnersDto;
import is.tech.dtos.OwnerEntityDto;
import is.tech.dtos.PageDto;
import is.tech.owners.contracts.OwnerService;
import is.tech.owners.daos.OwnerEntityRepository;
import is.tech.owners.domain.mappers.OwnerMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static is.tech.owners.rabbitmq.OwnersRabbitMQConfig.*;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerEntityRepository ownerEntityRepository;

    @RabbitListener(queues = GET_BY_ID_QUEUE)
    @Override
    public OwnerEntityDto getOwnerById(long id) {
        return OwnerMapper.toDto(ownerEntityRepository.getOwnerEntityByOwnerId(id));
    }

    @RabbitListener(queues = CREATE_QUEUE)
    @Override
    public OwnerEntityDto createOwner(OwnerEntityDto user) {
        if (ownerEntityRepository.existsById(user.getOwnerId())) {
            return OwnerMapper.toDto(ownerEntityRepository.getOwnerEntityByOwnerId(user.getOwnerId()));
        }
        return OwnerMapper.toDto(ownerEntityRepository.save(OwnerMapper.toEntity(user)));
    }

    @RabbitListener(queues = GET_ALL_QUEUE)
    @Override
    public PageDto<OwnerEntityDto> getAllOwners(GetAllOwnersDto dto) {
        Pageable paging = PageRequest.of(dto.getPage(), dto.getSize());
        return new PageDto<>(ownerEntityRepository.findAll(paging).map(OwnerMapper::toDto));
    }

    @RabbitListener(queues = DELETE_BY_ID_QUEUE)
    @Override
    public void deleteOwnerById(long userId) {
        ownerEntityRepository.deleteById(userId);
    }

    @RabbitListener(queues = DELETE_ALL_QUEUE)
    @Override
    public void deleteAllOwners() {
        ownerEntityRepository.deleteAll();
    }
}
