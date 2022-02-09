package com.ssafy.a105.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRegisterRes {
    long id;

    public static RoomRegisterRes of(Long id) {
        RoomRegisterRes roomRegisterRes = new RoomRegisterRes();
        roomRegisterRes.setId(id);
        return roomRegisterRes;
    }
}
