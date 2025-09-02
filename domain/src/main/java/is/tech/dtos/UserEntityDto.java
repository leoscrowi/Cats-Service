package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class UserEntityDto implements Serializable {
    private long userId;
    private String nickname;
    private String password;
    private Collection<RoleEntityDto> roles;
}
