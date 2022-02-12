package com.ssafy.a105.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserCountRes")
public class CheckRes {
    @ApiModelProperty(name="방 인원수 조회", example="5")
    long count;

    public static CheckRes of(long count) {
        CheckRes checkRes=new CheckRes();
        checkRes.count=count;
        return checkRes;
    }
}
