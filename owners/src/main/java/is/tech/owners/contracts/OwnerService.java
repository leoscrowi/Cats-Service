package is.tech.owners.contracts;

import is.tech.dtos.GetAllOwnersDto;
import is.tech.dtos.OwnerEntityDto;
import is.tech.dtos.PageDto;

public interface OwnerService {

    OwnerEntityDto getOwnerById(long id);

    OwnerEntityDto createOwner(OwnerEntityDto user);

    PageDto<OwnerEntityDto> getAllOwners(GetAllOwnersDto dto);

    void deleteOwnerById(long userId);

    void deleteAllOwners();
}
