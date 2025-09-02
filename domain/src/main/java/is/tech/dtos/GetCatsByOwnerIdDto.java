package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCatsByOwnerIdDto {
    private long ownerId;
    private int page;
    private int size;
}
