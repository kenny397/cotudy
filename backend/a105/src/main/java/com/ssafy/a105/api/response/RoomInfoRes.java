package com.ssafy.a105.api.response;

import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.StudyClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomInfoRes {
    long roomId;
    long headCount;
    long roomMaxPeople;
    String roomTitle;
    String roomDescription;
    String roomThumbnail;
    StudyClass roomCategory;
    public static RoomInfoRes of(long count, Room room){
        RoomInfoRes res = new RoomInfoRes();
        res.setRoomId(room.getId());
        res.setRoomMaxPeople(room.getMaxUser());
        res.setRoomTitle(room.getTitle());
        res.setRoomThumbnail(room.getThumbnail());
        res.setRoomDescription(room.getDescription());
        res.setRoomCategory(room.getStudyClass());
        res.setHeadCount(count);
        return res;
    }
}
