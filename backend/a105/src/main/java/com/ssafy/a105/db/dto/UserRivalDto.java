package com.ssafy.a105.db.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("UserRivalDto")
public class UserRivalDto {
    @ApiModelProperty(name="로그인 유저 PK", example="1")
    long userId;
    @ApiModelProperty(name="유저의 라이벌 PK", example="2")
    long rivalId;

    public static UserRivalDto of(long userId, long rivalId) {
        UserRivalDto body = new UserRivalDto();
        body.userId = userId;
        body.rivalId = rivalId;
        return body;
    }
}
