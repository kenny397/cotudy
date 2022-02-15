package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 출석일 API ([GET] /api/v1/users/calendar) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("RankingListGetResponse")
public class CalendarRes {
    String attendanceDate;

    public CalendarRes(){ }

    @QueryProjection
    public CalendarRes(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public static CalendarRes of(String attendanceDate) {
        CalendarRes res = new CalendarRes();
        res.setAttendanceDate(attendanceDate);
        return res;
    }


}
