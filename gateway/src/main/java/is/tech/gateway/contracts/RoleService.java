package is.tech.gateway.contracts;

import is.tech.gateway.domain.entities.RoleEntity;
import is.tech.gateway.domain.entities.RoleType;

import java.util.Collection;

public interface RoleService {

    Collection<RoleEntity> getRolesByRoleType(RoleType role);

    boolean hasAnyRole(String... roles);

    boolean hasAnyRoleByCatId(Long catId, String... roles);
}
