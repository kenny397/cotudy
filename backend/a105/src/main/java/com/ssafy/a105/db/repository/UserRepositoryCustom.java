package com.ssafy.a105.db.repository;

import com.ssafy.a105.api.response.CalendarRes;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.db.dto.AttendanceDateDto;

import java.util.List;

public interface UserRepositoryCustom {
    List<CalendarRes> getAttendanceDaysByUser(AttendanceDateDto userDateInfo);
    StudyTimeRes getDayStudyTimeByUser(long id);
    StudyTimeRes getTotalStudyTimeByUser(long id);
}
