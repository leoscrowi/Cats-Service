package is.tech.cats.domain.mappers;

import is.tech.cats.domain.entities.CatEntity;
import is.tech.cats.domain.entities.Color;
import is.tech.dtos.CatEntityDto;

public class CatMapper {

    public static CatEntity toEntity(CatEntityDto catEntityDto) {
        CatEntity catEntity = new CatEntity();
        catEntity.setCatId(catEntityDto.getCatId());
        catEntity.setName(catEntityDto.getName());
        catEntity.setColor(Color.valueOf(catEntityDto.getColor()));
        catEntity.setBreed(catEntityDto.getBreed());
        catEntity.setOwnerId(catEntityDto.getOwnerId());
        return catEntity;
    }

    public static CatEntityDto toDto(CatEntity catEntity) {
        CatEntityDto catEntityDto = new CatEntityDto();
        catEntityDto.setCatId(catEntity.getCatId());
        catEntityDto.setName(catEntity.getName());
        catEntityDto.setColor(catEntity.getColor().toString());
        catEntityDto.setBreed(catEntity.getBreed());
        catEntityDto.setOwnerId(catEntity.getOwnerId());
        return catEntityDto;
    }
}
