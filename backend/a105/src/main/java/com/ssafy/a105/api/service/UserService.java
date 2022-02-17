package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RivalPostReq;
import com.ssafy.a105.api.request.UserPostReq;
import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.CalendarRes;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.api.response.UserRecentWeekStudyTimeRes;
import com.ssafy.a105.api.response.UserRes;
import com.ssafy.a105.db.dto.AttendanceDateDto;
import com.ssafy.a105.db.dto.UserRivalDto;
import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserRegisterPostReq registerInfo);

    void deleteUser(long userId);

    User modifyUser(UserPostReq userInfo);

    UserRes getUserInfo(long userId);

    long getCheckEmail(String email);

    long getCheckNickName(String nickName);

    List<Rival> getRivals(long id);

    void registerRival(RivalPostReq rivalPostReq);

    void deleteRival(RivalPostReq rivalPostReq);

    StudyTimeRes getDayStudyTime(long id);

    StudyTimeRes getTotalStudyTime(long id);

    User getUserByUserId(String userId);

    List<CalendarRes> getAttendanceDays(AttendanceDateDto userDateInfo);

    int isRival(UserRivalDto userRivalDto);

    List<UserRecentWeekStudyTimeRes> getListRecentWeekStudyTime(int id);
}
