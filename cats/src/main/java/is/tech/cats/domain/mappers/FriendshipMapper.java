package is.tech.cats.domain.mappers;

import is.tech.cats.domain.entities.FriendEntity;
import is.tech.dtos.FriendEntityDto;

public class FriendshipMapper {

    public static FriendEntityDto toDto(FriendEntity friend) {
        FriendEntityDto friendDto = new FriendEntityDto();
        friendDto.setFriendshipId(friend.getFriendshipId());
        friendDto.setFirstCatId(friend.getFirstCatId());
        friendDto.setSecondCatId(friend.getSecondCatId());
        return friendDto;
    }

    public static FriendEntity toEntity(FriendEntityDto friend) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFriendshipId(friend.getFriendshipId());
        friendEntity.setFirstCatId(friend.getFirstCatId());
        friendEntity.setSecondCatId(friend.getSecondCatId());
        return friendEntity;
    }
}
