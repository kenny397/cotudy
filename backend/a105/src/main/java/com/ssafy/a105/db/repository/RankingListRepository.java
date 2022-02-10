package com.ssafy.a105.db.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.api.response.QRankingListGetRes;
import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import com.ssafy.a105.db.entity.QStudyTime;
import com.ssafy.a105.db.entity.StudyTime;
import com.ssafy.a105.db.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RankingListRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    @Autowired
    public RivalRepository rivalRepository;
    @Autowired
    public UserRepository userRepository;

    public RankingListRepository(JPAQueryFactory jpaQueryFactory) {
        super(StudyTime.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PageImpl<RankingListGetRes> getTotalStudyTimeByUserPaging(@NotNull RankingListDto rankingInfo, Pageable pageable) {
        QStudyTime qStudyTime = QStudyTime.studyTime;

        JPQLQuery<RankingListGetRes> query = jpaQueryFactory.select(new QRankingListGetRes(qStudyTime.user.nickname, qStudyTime.user.department.name, qStudyTime.time.sum()))
                .from(qStudyTime).where(eqCategory(rankingInfo.getKeyCategory()), eqNickName(rankingInfo.getSearchUserNickname()), eqPeriod(rankingInfo.getKeyPeriod()), eqDepartment(rankingInfo.getLoginUserPid(),rankingInfo.getKeyDepartment()))
                .groupBy(qStudyTime.user).orderBy(qStudyTime.time.sum().desc());

        long totalCount = query.fetchCount(); // 2)
        List<RankingListGetRes> results = getQuerydsl().applyPagination(pageable, query).fetch();  // 3)
        return new PageImpl<>(results, pageable, totalCount);  // 4)

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
/**
    public PageImpl<RankingListGetRes2> getTotalStudyTimeByUserPaging2(Pageable pageable) {
        QStudyTime qStudyTime = QStudyTime.studyTime;
        QStudyTime qStudyTime2 = QStudyTime.studyTime;
        JPQLQuery<RankingListGetRes2> query = jpaQueryFactory.select(new QRankingListGetRes2(qStudyTime.user.nickname, qStudyTime.user.department.name, qStudyTime.time.sum(), ExpressionUtils.as(JPAExpressions.select(count(qStudyTime.time.sum())).from(qStudyTime).where(qStudyTime.time.sum().gt(qStudyTime.time.sum())),"rank")))
                .from(qStudyTime)
                .groupBy(qStudyTime.user).orderBy(qStudyTime.time.sum().desc());

        long totalCount = query.fetchCount(); // 2)
        List<RankingListGetRes2> results = getQuerydsl().applyPagination(pageable, query).fetch();  // 3)
        return new PageImpl<>(results, pageable, totalCount);  // 4)

    }
*/
    private BooleanExpression eqCategory(String category) {
        if (category.equals("all") || category.isEmpty()) {
            return null;
        }
        return QStudyTime.studyTime.studyClass.name.eq(category);
    }

    private BooleanExpression eqNickName(String nickName) {
        if (nickName.equals("all") || nickName.isEmpty()) {
            return null;
        }
        return QStudyTime.studyTime.user.nickname.contains(nickName);
    }

    private BooleanExpression eqPeriod(String period) {
        if (period.equals("all") || period.isEmpty()) {
            return null;
        }
        int days = calculateDays(period);
        return QStudyTime.studyTime.createdDate.between(LocalDateTime.now().minusDays(days), LocalDateTime.now());

    }

    private BooleanExpression eqDepartment(long userPid, String department) {
        if (userPid == 0 || department.equals("all") || department.isEmpty()) {
            return null;
        }
        if (department.equals("department")) {
            User user = userRepository.findById(userPid);
            return QStudyTime.studyTime.user.department.name.eq(user.getDepartment().getName());
        }
//        List<Rival> rivalList = rivalRepository.findByUserId(userPid);
//        BooleanExpression query = eqRivalList(rivalList);
//        for(int i = 0; i<rivalList.size(); i++){
//
//        }
        return null;
    }
/**
    private BooleanExpression eqRivalList(List<Rival> rivalList) {
        if(rivalList.isEmpty())
            return null;
        for(int i = 0; i<rivalList.size(); i++){

        }
        return null;
    }

    private BooleanExpression eqRival(Long rivalId) {
        return rivalId != null ? QStudyTime.studyTime.user.id.eq(rivalId) : null;
    }
*/
    private int calculateDays(String period) {
        int days = 365;
        LocalDateTime now = LocalDateTime.now();
        if (period.equals("day"))
            return 1;
        if (period.equals("week"))
            return 7;
        if (period.equals("month")) { // 나중에 월별 일자 구분 로직
            int year = now.getYear();
            int month = now.getMonthValue();
            boolean isLeapYear = false;
            if ((year % 4 == 0) && (year % 100 != 0) && (year % 400 == 0)) {
                isLeapYear = true;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    return 31;
                case 2:
                    if (isLeapYear)
                        return 29;
                    else
                        return 28;
                default:
                    return 30;
            }
        }
        return days;
    }
}
