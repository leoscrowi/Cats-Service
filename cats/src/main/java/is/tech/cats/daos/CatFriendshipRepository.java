package is.tech.cats.daos;

import is.tech.cats.domain.entities.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CatFriendshipRepository extends JpaRepository<FriendEntity, Long> {
    Collection<? extends FriendEntity> getAllByFirstCatId(long firstCatId);

    Collection<? extends FriendEntity> getAllBySecondCatId(long secondCatId);
}
