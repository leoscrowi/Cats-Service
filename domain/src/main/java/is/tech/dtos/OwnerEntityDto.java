package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerEntityDto {
    private long ownerId;
    private String name;
    private long userId;
}
