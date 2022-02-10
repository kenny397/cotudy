package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RivalPostReq;
import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserRegisterPostReq registerInfo);

    void deleteUser(long userId);

    User modifyUser(UserRegisterPostReq registerInfo);

    User getUserInfo(long userId);

    long getCheckEmail(String email);

    long getCheckNickName(String nickName);

    List<Rival> getRivals(long id);

    void registerRival(RivalPostReq rivalPostReq);

    void deleteRival(RivalPostReq rivalPostReq);

    StudyTimeRes getDayStudyTime(long id);

    StudyTimeRes getTotalStudyTime(long id);
}
