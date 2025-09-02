package is.tech.gateway.contracts;

import is.tech.dtos.UserEntityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntityDto getUserById(long id);

    UserEntityDto createUser(UserEntityDto user);

    Page<UserEntityDto> getAllUsers(Pageable paging);

    void deleteUserById(long userId);

    void deleteAllUsers();
}
