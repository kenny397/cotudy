package com.ssafy.a105.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.db.entity.StudyTime;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;

public class RankingListRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public RankingListRepository(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        super(StudyTime.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }
    /**
    public PageImpl<RankingListGetRes> getTotalStudyTimesByUserPaging(Pageable pageable) {
        QStudyTime qStudyTime = QStudyTime.studyTime;

        JPQLQuery<RankingListGetRes> query = jpaQueryFactory.select(new RankingListGetRes(qStudyTime.user, qStudyTime.time.sum().castToNum(Integer.class)))
                .from(qStudyTime)
                .groupBy(qStudyTime.user);

        long totalCount = query.fetchCount(); // 2)
        List<RankingListGetRes> results = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();  // 3)
        return new PageImpl<>(results, pageable, totalCount);  // 4)

    }
     */
}
