package is.tech.gateway.controllers;

import is.tech.dtos.OwnerEntityDto;
import is.tech.dtos.UserEntityDto;
import is.tech.gateway.contracts.RoleService;
import is.tech.gateway.contracts.UserService;

import is.tech.gateway.domain.entities.RoleType;
import is.tech.gateway.domain.mappers.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RabbitTemplate rabbitTemplate;
    private final PasswordEncoder passwordEncoder;

    static final String EXCHANGE_NAME = "owners-exchange";

    @PostMapping("/register")
    public ResponseEntity<UserEntityDto> registerUser(@RequestBody UserEntityDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(RoleMapper.toDto(roleService.getRolesByRoleType(RoleType.USER)));
        UserEntityDto userEntity = userService.createUser(userDto);

        OwnerEntityDto ownerDto = new OwnerEntityDto();
        ownerDto.setUserId(userEntity.getUserId());
        ownerDto.setName(userDto.getNickname());

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "create", ownerDto);

        userEntity.setPassword(null);

        return ResponseEntity.ok(userEntity);
    }
}
