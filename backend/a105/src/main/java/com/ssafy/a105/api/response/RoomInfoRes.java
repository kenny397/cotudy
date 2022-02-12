package com.ssafy.a105.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.StudyClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomInfoResponse")
public class RoomInfoRes {
    @ApiModelProperty(name="방 PK정보", example="1")
    long roomId;
    @ApiModelProperty(name="방 인원수 정보", example="3")
    long headCount;
    @ApiModelProperty(name="방 최대인원 정보", example="8")
    long roomMaxPeople;
    @ApiModelProperty(name="방 제목", example="자격증 공부방")
    String roomTitle;
    @ApiModelProperty(name="방 설명", example="26이상만 가능합니다.")
    String roomDescription;
    @ApiModelProperty(name="방 썸네일", example="1")
    String roomThumbnail;
    @ApiModelProperty(name="방 카테고리", example="2")
    String roomCategory;
    public static RoomInfoRes of(long count, Room room){
        RoomInfoRes res = new RoomInfoRes();
        res.setRoomId(room.getId());
        res.setRoomMaxPeople(room.getMaxUser());
        res.setRoomTitle(room.getTitle());
        res.setRoomThumbnail(room.getThumbnail());
        res.setRoomDescription(room.getDescription());
        res.setRoomCategory(room.getStudyClass().getId().toString());
        res.setHeadCount(count);
        return res;
    }
}
