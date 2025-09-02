package is.tech.gateway.daos;

import is.tech.gateway.domain.entities.RoleEntity;
import is.tech.gateway.domain.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    Collection<RoleEntity> getAllByRole(RoleType role);
}
