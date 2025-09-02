package is.tech.gateway.controllers;

import is.tech.dtos.CatEntityDto;
import is.tech.dtos.GetCatsByOwnerIdDto;
import is.tech.dtos.GetCatsPageDto;
import is.tech.dtos.PageDto;
import is.tech.gateway.domain.entities.Color;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/cats")
public class CatsController {

    private final RabbitTemplate rabbitTemplate;

    static final String EXCHANGE_NAME = "cats-exchange";

    @PostMapping
    public ResponseEntity<CatEntityDto> createCat(@RequestBody CatEntityDto catDto) {
        CatEntityDto catEntityDto = (CatEntityDto) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "create", catDto);
        return ResponseEntity.ok(catEntityDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatEntityDto> getCatById(@PathVariable Long id) {
        CatEntityDto catEntityDto = (CatEntityDto) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-by-id", id);
        return ResponseEntity.ok(catEntityDto);
    }

    @GetMapping
    public ResponseEntity<PageDto<CatEntityDto>> getCats(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) String breed,
                                                         @RequestParam(required = false) Color color) {
        GetCatsPageDto dto = new GetCatsPageDto();
        dto.setPage(page);
        dto.setSize(size);
        dto.setBreed(breed);
        String colorName = color != null ? color.name() : null;

        dto.setColor(colorName);

        PageDto<CatEntityDto> catEntityDtos = (PageDto<CatEntityDto>) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-all", dto);
        return ResponseEntity.ok(catEntityDtos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@roleServiceImpl.hasAnyRoleByCatId(#id, 'USER', 'ADMIN')")
    public ResponseEntity deleteCat(@PathVariable Long id) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-by-id", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    @PreAuthorize("@roleServiceImpl.hasAnyRole('ADMIN')")
    public ResponseEntity deleteAllCats() {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "delete-all");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<PageDto<CatEntityDto>> getCatsByOwnerId(@RequestParam(defaultValue = "0") int page,
                                                                  @PathVariable Long ownerId,
                                                                  @RequestParam(defaultValue = "10") int size) {
        GetCatsByOwnerIdDto dto = new GetCatsByOwnerIdDto();
        dto.setPage(page);
        dto.setSize(size);

        PageDto<CatEntityDto> catEntityDtos = (PageDto<CatEntityDto>) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, "get-by-owner-id", dto);
        return ResponseEntity.ok(catEntityDtos);
    }
}