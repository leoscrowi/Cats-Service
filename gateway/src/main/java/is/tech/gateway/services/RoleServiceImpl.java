package is.tech.gateway.services;

import is.tech.dtos.CatEntityDto;
import is.tech.gateway.contracts.RoleService;
import is.tech.gateway.daos.RoleEntityRepository;
import is.tech.gateway.daos.UserEntityRepository;
import is.tech.gateway.domain.entities.RoleEntity;
import is.tech.gateway.domain.entities.RoleType;
import is.tech.gateway.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleEntityRepository roleEntityRepository;
    private final UserEntityRepository userEntityRepository;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Collection<RoleEntity> getRolesByRoleType(RoleType role) {
        Collection<RoleEntity> roles = roleEntityRepository.getAllByRole(role);
        return roles;
    }

    @Override
    public boolean hasAnyRole(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        UserEntity userEntity = userEntityRepository.getUserEntityByNickname(auth.getName());
        if (userEntity == null) {
            return false;
        }

        Set<RoleType> userRoles = userEntity.getRoles().stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());

        for (var roleType : userRoles) {
            if (roleType == RoleType.ADMIN) return true;
        }

        for (String role : roles) {
            try {
                RoleType requiredRole = RoleType.valueOf(role);
                if (userRoles.contains(requiredRole)) return true;
            } catch (IllegalArgumentException ignored) {
            }
        }

        return false;
    }

    @Override
    public boolean hasAnyRoleByCatId(Long catId, String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;

        CatEntityDto catEntityDto = (CatEntityDto) rabbitTemplate.convertSendAndReceive("cats-exchange", "get-by-id", catId);
        if (catEntityDto == null) return false;

        UserEntity userEntity = userEntityRepository.getUserEntityByNickname(auth.getName());
        if (userEntity == null) return false;

        Set<RoleType> userRoles = userEntity.getRoles().stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());

        for (var roleType : userRoles) {
            if (roleType == RoleType.ADMIN) return true;
        }

        if (catEntityDto.getOwnerId() != userEntity.getUserId()) return false;

        for (String role : roles) {
            try {
                RoleType requiredRole = RoleType.valueOf(role);
                if (userRoles.contains(requiredRole)) return true;
            } catch (IllegalArgumentException ignored) {
                return false;
            }
        }

        return true;
    }
}