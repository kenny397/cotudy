package com.ssafy.a105.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegisterRequest")
public class UserRegisterPostReq {
    @ApiModelProperty(name="유저 ID", example="ssafy@ssafy.com")
    String email;
    @ApiModelProperty(name="유저 닉네임", example="열공맨")
    String nickName;
    @ApiModelProperty(name="유저 비밀번호", example="your_password")
    String password;
}
