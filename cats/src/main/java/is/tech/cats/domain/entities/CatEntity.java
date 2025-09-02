package is.tech.cats.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cats")
public class CatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long catId;

    private String name;

    private String breed;

    @Enumerated
    @Column(name = "color")
    private Color color;

    @Column(nullable = false)
    private long ownerId;
}
