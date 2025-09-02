package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCatsPageDto {
    private int page;
    private int size;
    private String breed;
    private String color;
}
