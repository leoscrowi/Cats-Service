package is.tech.cats.daos;

import is.tech.cats.domain.entities.CatEntity;
import is.tech.cats.domain.entities.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatEntityRepository extends JpaRepository<CatEntity, Long> {

    CatEntity getCatEntityByCatId(Long catId);

    @Query("""
            SELECT c FROM CatEntity c
            WHERE (:color IS NULL OR c.color = :color)
              AND (:breed IS NULL OR c.breed = :breed)
            """)
    Page<CatEntity> getCatEntitiesByFilter(Pageable pageable, @Param("color") Color color, @Param("breed") String breed);

    Page<CatEntity> getCatEntitiesByOwnerId(long ownerId, Pageable pageable);

    List<CatEntity> getCatEntitiesByOwnerId(long ownerId);
}
