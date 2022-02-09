package com.ssafy.a105.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomExitPostReq {
    String userId;
    String roomId;
    Long studyTime;

}
