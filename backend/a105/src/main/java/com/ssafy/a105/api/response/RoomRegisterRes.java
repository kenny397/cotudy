package com.ssafy.a105.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomPkRes")
public class RoomRegisterRes {
    @ApiModelProperty(name="방 PK정보", example="1")
    long id;

    public static RoomRegisterRes of(Long id) {
        RoomRegisterRes roomRegisterRes = new RoomRegisterRes();
        roomRegisterRes.setId(id);
        return roomRegisterRes;
    }
}
