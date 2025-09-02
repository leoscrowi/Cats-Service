package is.tech.gateway.controllers;

import is.tech.dtos.FriendEntityDto;
import is.tech.dtos.GetCatsFriendsDto;
import is.tech.dtos.PageDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendshipController {

    private final RabbitTemplate rabbitTemplate;

    static final String EXCHANGE_NAME = "cats-exchange";

    @DeleteMapping()
    public ResponseEntity deleteAllFriendships(long id) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-all-friendships", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@roleServiceImpl.hasAnyRole('ADMIN')")
    public ResponseEntity deleteFriendship(@PathVariable long id) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-friendship", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<FriendEntityDto> createFriendship(@RequestBody FriendEntityDto friend) {
        rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "create-friendship", friend);
        return ResponseEntity.ok().body(friend);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageDto<FriendEntityDto>> getCatsFriends(@PathVariable long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        GetCatsFriendsDto dto = new GetCatsFriendsDto();

        dto.setPage(page);
        dto.setSize(size);
        dto.setCatId(id);

        PageDto<FriendEntityDto> friendEntityDtos = (PageDto<FriendEntityDto>) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-all-friendships", dto);
        return ResponseEntity.ok(friendEntityDtos);
    }
}
