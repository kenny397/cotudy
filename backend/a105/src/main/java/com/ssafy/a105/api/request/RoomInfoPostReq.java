package com.ssafy.a105.api.request;

import com.ssafy.a105.db.entity.StudyClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomInfoPostReq {
    String roomTitle;
    int roomMaxPeople;
    String roomDescription;
    String roomThumbnail;
    String roomCategory;
}
