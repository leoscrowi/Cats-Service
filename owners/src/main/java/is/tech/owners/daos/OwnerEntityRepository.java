package is.tech.owners.daos;

import is.tech.owners.domain.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerEntityRepository extends JpaRepository<OwnerEntity, Long> {
    OwnerEntity getOwnerEntityByOwnerId(long ownerId);
}
