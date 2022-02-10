package com.ssafy.a105.db.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.api.response.QStudyTimeRes;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.db.entity.QStudyTime;
import com.ssafy.a105.db.entity.StudyTime;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class UserCustomRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public UserCustomRepository(JPAQueryFactory jpaQueryFactory) {
        super(StudyTime.class);
        this.jpaQueryFactory= jpaQueryFactory;
    }
    public StudyTimeRes getDayStudyTimeByUser(long id){
        QStudyTime qstudyTime = QStudyTime.studyTime;

        JPQLQuery<StudyTimeRes> query = jpaQueryFactory.select(new QStudyTimeRes(qstudyTime.user.id,qstudyTime.user.userId,qstudyTime.time.sum()))
                .from(qstudyTime).where(QStudyTime.studyTime.createdDate.between(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),LocalDateTime.now()))
                .groupBy(qstudyTime.user).having(QStudyTime.studyTime.user.id.eq(id));

        StudyTimeRes result= query.fetchFirst();
        return result;
    }


    public StudyTimeRes getTotalStudyTimeByUser(long id) {
        QStudyTime qstudyTime = QStudyTime.studyTime;

        JPQLQuery<StudyTimeRes> query = jpaQueryFactory.select(new QStudyTimeRes(qstudyTime.user.id,qstudyTime.user.userId,qstudyTime.time.sum()))
                .from(qstudyTime)
                .groupBy(qstudyTime.user).having(QStudyTime.studyTime.user.id.eq(id));

        StudyTimeRes result= query.fetchFirst();
        return result;
    }
}
