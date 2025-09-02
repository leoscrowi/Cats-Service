package is.tech.gateway.domain.mappers;

import is.tech.dtos.UserEntityDto;
import is.tech.gateway.domain.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserEntityDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setNickname(userDto.getNickname());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setRoles(RoleMapper.toEntity(userDto.getRoles()));
        return userEntity;
    }

    public static UserEntityDto toDto(UserEntity userEntity) {
        UserEntityDto userDto = new UserEntityDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setNickname(userEntity.getNickname());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRoles(RoleMapper.toDto(userEntity.getRoles()));
        return userDto;
    }
}
