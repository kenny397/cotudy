package com.ssafy.a105.db.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.api.response.QRankingListGetRes;
import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.db.entity.QStudyTime;
import com.ssafy.a105.db.entity.StudyTime;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RankingListRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public RankingListRepository(JPAQueryFactory jpaQueryFactory) {
        super(StudyTime.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PageImpl<RankingListGetRes> getTotalStudyTimeByUserPaging(Pageable pageable) {
        QStudyTime qStudyTime = QStudyTime.studyTime;

        JPQLQuery<RankingListGetRes> query = jpaQueryFactory.select(new QRankingListGetRes(qStudyTime.user.nickname, qStudyTime.user.department.name, qStudyTime.time.sum()))
                .from(qStudyTime)
                .groupBy(qStudyTime.user).orderBy(qStudyTime.time.sum().desc());

        long totalCount = query.fetchCount(); // 2)
        List<RankingListGetRes> results = getQuerydsl().applyPagination(pageable, query).fetch();  // 3)
        return new PageImpl<>(results, pageable, totalCount);  // 4)

    }

}
