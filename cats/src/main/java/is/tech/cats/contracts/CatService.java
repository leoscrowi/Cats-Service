package is.tech.cats.contracts;

import is.tech.dtos.CatEntityDto;
import is.tech.dtos.GetCatsByOwnerIdDto;
import is.tech.dtos.GetCatsPageDto;
import is.tech.dtos.PageDto;

import java.util.List;

public interface CatService {
    CatEntityDto getCatById(long catId);

    PageDto<CatEntityDto> getAllCats(GetCatsPageDto catPageDto);

    CatEntityDto createCat(CatEntityDto cat);

    void deleteCatById(long catId);

    void deleteAllCats();

    PageDto<CatEntityDto> getCatsByOwnerId(GetCatsByOwnerIdDto dto);

    List<CatEntityDto> getCatsByOwnerId(long ownerId);
}
