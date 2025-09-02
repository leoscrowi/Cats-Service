package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendEntityDto {
    private Long friendshipId;
    private long firstCatId;
    private long secondCatId;
}
