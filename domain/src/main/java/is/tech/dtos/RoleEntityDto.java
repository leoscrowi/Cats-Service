package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleEntityDto implements Serializable {
    private Long roleId;
    private String role;
}
