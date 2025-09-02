package is.tech.owners.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "owners")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ownerId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private long userId;
}
