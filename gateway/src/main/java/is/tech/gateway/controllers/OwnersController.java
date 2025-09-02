package is.tech.gateway.controllers;

import is.tech.dtos.GetAllOwnersDto;
import is.tech.dtos.OwnerEntityDto;
import is.tech.dtos.PageDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnersController {

    private final RabbitTemplate rabbitTemplate;

    static final String EXCHANGE_NAME = "owners-exchange";

    @GetMapping("/{id}")
    public ResponseEntity<OwnerEntityDto> getOwnerById(@PathVariable Long id) {
        OwnerEntityDto ownerEntityDto = (OwnerEntityDto) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-by-id", id);
        return ResponseEntity.ok(ownerEntityDto);
    }

    @GetMapping
    public ResponseEntity<PageDto<OwnerEntityDto>> getOwners(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        GetAllOwnersDto dto = new GetAllOwnersDto();
        dto.setPage(page);
        dto.setSize(size);

        PageDto<OwnerEntityDto> catEntityDtos = (PageDto<OwnerEntityDto>) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-all", dto);
        return ResponseEntity.ok(catEntityDtos);
    }

    @PostMapping
    public ResponseEntity<OwnerEntityDto> createOwner(@RequestBody OwnerEntityDto ownerEntityDto) {
        OwnerEntityDto createdOwner = (OwnerEntityDto) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "create", ownerEntityDto);
        return ResponseEntity.ok(createdOwner);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@roleServiceImpl.hasAnyRole('ADMIN')")
    public ResponseEntity<OwnerEntityDto> deleteOwner(@PathVariable Long id) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-by-id", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("@roleServiceImpl.hasAnyRole('ADMIN')")
    public ResponseEntity<OwnerEntityDto> deleteAllOwners() {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-all");
        return ResponseEntity.ok().build();
    }
}
