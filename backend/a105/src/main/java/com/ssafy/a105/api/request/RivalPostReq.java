package com.ssafy.a105.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RivalPostRequest")
public class RivalPostReq {
    @ApiModelProperty(name="유저 PK정보", example="1")
    long userId;
    @ApiModelProperty(name="라이벌 유저 PK정보", example="2")
    long rivalId;
}
