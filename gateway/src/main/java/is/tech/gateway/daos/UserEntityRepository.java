package is.tech.gateway.daos;

import is.tech.gateway.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getUserEntityByNickname(String nickname);

    UserEntity getUserEntityByUserId(long userId);
}
