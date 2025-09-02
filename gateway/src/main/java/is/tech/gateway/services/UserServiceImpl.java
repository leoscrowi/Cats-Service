package is.tech.gateway.services;

import is.tech.dtos.UserEntityDto;
import is.tech.gateway.contracts.UserService;
import is.tech.gateway.daos.UserEntityRepository;
import is.tech.gateway.domain.entities.UserEntity;
import is.tech.gateway.domain.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserEntityDto getUserById(long id) {
        return UserMapper.toDto(userEntityRepository.getUserEntityByUserId(id));
    }

    @Override
    public UserEntityDto createUser(UserEntityDto user) {
        return UserMapper.toDto(userEntityRepository.save(UserMapper.toEntity(user)));
    }

    @Override
    public Page<UserEntityDto> getAllUsers(Pageable paging) {
        return userEntityRepository.findAll(paging).map(UserMapper::toDto);
    }

    @Override
    public void deleteUserById(long userId) {
        userEntityRepository.deleteById(userId);
    }

    @Override
    public void deleteAllUsers() {
        userEntityRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.getUserEntityByNickname(nickname);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(user.getNickname(), user.getPassword(), user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .toList());
    }
}
