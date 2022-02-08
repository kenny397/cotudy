package com.ssafy.a105.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterPostReq {
    String email;
    String nickName;
    String password;
}
