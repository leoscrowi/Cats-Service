package is.tech.cats.contracts;

import is.tech.dtos.FriendEntityDto;
import is.tech.dtos.GetCatsFriendsDto;
import is.tech.dtos.PageDto;

public interface CatFriendshipService {
    PageDto<FriendEntityDto> getCatsFriends(GetCatsFriendsDto dto);

    void deleteCatFriendship(long friendshipId);

    FriendEntityDto createCatFriendship(FriendEntityDto catFriendship);
}
