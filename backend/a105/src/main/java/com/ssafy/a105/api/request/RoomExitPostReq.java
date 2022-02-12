package com.ssafy.a105.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomExitRequest")
public class RoomExitPostReq {
    @ApiModelProperty(name="유저 PK정보", example="1")
    long userId;
    @ApiModelProperty(name="방 PK정보", example="1")
    long roomId;
    @ApiModelProperty(name="공부시간", example="6시간 30분은 390")
    long studyTime;

}
