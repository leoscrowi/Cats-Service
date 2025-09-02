package is.tech.cats.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long friendshipId;

    private long firstCatId;

    private long secondCatId;

}
