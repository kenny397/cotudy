package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.db.entity.User;

public interface UserService {
    User createUser(UserRegisterPostReq registerInfo);

    void deleteUser(long userId);

    User modifyUser(UserRegisterPostReq registerInfo);

    User getUserInfo(long userId);

    long getCheckEmail(String email);

    long getCheckNickName(String nickName);
}
