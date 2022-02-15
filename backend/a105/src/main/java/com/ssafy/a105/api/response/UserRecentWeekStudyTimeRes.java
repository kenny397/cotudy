package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저  API ([POST] /api/v1/users/time/week) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UserRecentWeekStudyTimeResponse")
public class UserRecentWeekStudyTimeRes {
    String date;
    long studyTime;

    public UserRecentWeekStudyTimeRes(){}

    @QueryProjection
    public UserRecentWeekStudyTimeRes(String date, long studyTime){
        this.date = date;
        this.studyTime = studyTime;
    }

    public static UserRecentWeekStudyTimeRes of(String date, long studyTime){
        UserRecentWeekStudyTimeRes body = new UserRecentWeekStudyTimeRes();
        body.date = date;
        body.studyTime = studyTime;
        return body;
    }
}
