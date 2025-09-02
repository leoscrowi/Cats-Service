package is.tech.gateway.domain.mappers;

import is.tech.dtos.RoleEntityDto;
import is.tech.gateway.domain.entities.RoleEntity;
import is.tech.gateway.domain.entities.RoleType;

import java.util.Collection;
import java.util.HashSet;

public class RoleMapper {

    public static Collection<RoleEntity> toEntity(Collection<RoleEntityDto> roleDtos) {
        Collection<RoleEntity> roleEntities = new HashSet<>();
        for (RoleEntityDto roleDto : roleDtos) {
            roleEntities.add(RoleMapper.toEntity(roleDto));
        }
        return roleEntities;
    }

    public static Collection<RoleEntityDto> toDto(Collection<RoleEntity> roleEntities) {
        Collection<RoleEntityDto> roleDtos = new HashSet<>();
        for (RoleEntity roleEntity : roleEntities) {
            roleDtos.add(RoleMapper.toDto(roleEntity));
        }
        return roleDtos;
    }

    public static RoleEntity toEntity(RoleEntityDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleType.valueOf(roleDto.getRole()));
        roleEntity.setRoleId(roleDto.getRoleId());
        return roleEntity;
    }

    public static RoleEntityDto toDto(RoleEntity roleEntity) {
        RoleEntityDto roleEntityDto = new RoleEntityDto();
        roleEntityDto.setRoleId(roleEntity.getRoleId());
        roleEntityDto.setRole(roleEntity.getRole().toString());
        return roleEntityDto;
    }
}
