package com.ssafy.a105.db.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.api.response.CalendarRes;
import com.ssafy.a105.api.response.QCalendarRes;
import com.ssafy.a105.api.response.QStudyTimeRes;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.db.dto.AttendanceDateDto;
import com.ssafy.a105.db.entity.QStudyTime;
import com.ssafy.a105.db.entity.StudyTime;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserCustomRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final static QStudyTime qStudyTime = QStudyTime.studyTime;
    private final static int HALF_HOUR = 30;

    public UserCustomRepository(JPAQueryFactory jpaQueryFactory) {
        super(StudyTime.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CalendarRes> getAttendanceDaysByUser(AttendanceDateDto userDateInfo) {
        List<CalendarRes> attendanceDayList = jpaQueryFactory.select(new QCalendarRes(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qStudyTime.createdDate, "%Y-%m-%d"))).from(qStudyTime)
                .where(qStudyTime.user.id.eq(userDateInfo.getId())).groupBy(qStudyTime.createdDate)
                .having(qStudyTime.time.sum().goe(HALF_HOUR).and(betweenDate(userDateInfo.getYear(), userDateInfo.getMonth())))
                .orderBy(qStudyTime.createdDate.asc()).fetch();

        return attendanceDayList;
    }

    private BooleanExpression betweenDate(int year, int month) {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        int nowYear = nowLocalDateTime.getYear();
        int nowMonth = nowLocalDateTime.getMonthValue();

        if (year == nowYear && month == nowMonth) {
            return qStudyTime.createdDate.between(LocalDateTime.of(year,month - 1, 22 , 0 , 0), nowLocalDateTime);
        }
        if (year > nowYear || year == nowYear && month > nowMonth)
            return null;

        return qStudyTime.createdDate.between(LocalDateTime.of(year, month - 1, 22 , 0, 0), LocalDateTime.of(year,month+1,1,0,0));
    }

    public StudyTimeRes getDayStudyTimeByUser(long id) {

        JPQLQuery<StudyTimeRes> query = jpaQueryFactory.select(new QStudyTimeRes(qStudyTime.user.id, qStudyTime.user.userId, qStudyTime.time.sum()))
                .from(qStudyTime).where(QStudyTime.studyTime.createdDate.between(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now()))
                .groupBy(qStudyTime.user).having(QStudyTime.studyTime.user.id.eq(id));

        StudyTimeRes result = query.fetchFirst();
        return result;
    }


    public StudyTimeRes getTotalStudyTimeByUser(long id) {

        JPQLQuery<StudyTimeRes> query = jpaQueryFactory.select(new QStudyTimeRes(qStudyTime.user.id, qStudyTime.user.userId, qStudyTime.time.sum()))
                .from(qStudyTime)
                .groupBy(qStudyTime.user).having(QStudyTime.studyTime.user.id.eq(id));

        StudyTimeRes result = query.fetchFirst();
        return result;
    }
}
