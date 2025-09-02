package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCatsFriendsDto {
    private long catId;
    private int page;
    private int size;
}
