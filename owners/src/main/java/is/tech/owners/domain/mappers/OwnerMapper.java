package is.tech.owners.domain.mappers;

import is.tech.dtos.OwnerEntityDto;
import is.tech.owners.domain.entities.OwnerEntity;

public class OwnerMapper {

    public static OwnerEntityDto toDto(OwnerEntity ownerEntity) {
        OwnerEntityDto dto = new OwnerEntityDto();
        dto.setOwnerId(ownerEntity.getOwnerId());
        dto.setName(ownerEntity.getName());
        dto.setUserId(ownerEntity.getUserId());
        return dto;
    }

    public static OwnerEntity toEntity(OwnerEntityDto dto) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setOwnerId(dto.getOwnerId());
        ownerEntity.setName(dto.getName());
        ownerEntity.setUserId(dto.getUserId());
        return ownerEntity;
    }
}
