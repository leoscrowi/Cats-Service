package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CatEntityDto implements Serializable {
    private Long catId;
    private String name;
    private String breed;
    private String color;
    private Long ownerId;
}
