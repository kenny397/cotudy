package com.ssafy.a105.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a105.api.response.QRankingListGetRes;
import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import com.ssafy.a105.db.entity.QStudyTime;
import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StudyTimeRepositoryImpl implements StudyTimeRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final RivalRepository rivalRepository;
    private final UserRepository userRepository;
    private final static QStudyTime qStudyTime = QStudyTime.studyTime;

    @Override
    public RankingRankGetRes getUserRankInfoByUser(long userPid) {

        List<RankingListGetRes> userList = jpaQueryFactory.select(new QRankingListGetRes(qStudyTime.user.nickname, qStudyTime.user.department.name, qStudyTime.time.sum()))
                .from(qStudyTime).groupBy(qStudyTime.user).orderBy(qStudyTime.time.sum().desc()).fetch();

        User user = userRepository.getById(userPid);
        for(int i = 0; i<userList.size();i++){
            RankingListGetRes order = userList.get(i);
            if(order.getNickName().equals(user.getNickname())){
                return new RankingRankGetRes(order.getNickName(), order.getDepartment(), order.getTotalStudyTime(), i+1);
            }
        }
        return null;
    }

    @Override
    public PageImpl<RankingListGetRes> getListByUser(RankingListDto rankingInfo, Pageable pageable) {

        QueryResults<RankingListGetRes> query = jpaQueryFactory.select(new QRankingListGetRes(qStudyTime.user.nickname, qStudyTime.user.department.name, qStudyTime.time.sum()))
                .from(qStudyTime).where(eqCategory(rankingInfo.getCategory()), eqNickName(rankingInfo.getUserNickname()), eqPeriod(rankingInfo.getTerm()), eqDepartment(rankingInfo.getUserId(), rankingInfo.getUserClass()))
                .groupBy(qStudyTime.user).orderBy(qStudyTime.time.sum().desc()).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetchResults();

        List<RankingListGetRes> content = query.getResults();
        long totalCount = query.getTotal(); // 2)
        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression eqCategory(String category) {
        if (category == null || category.isEmpty()) {
            return null;
        }
        return qStudyTime.studyClass.name.eq(category);
    }

    private BooleanExpression eqNickName(String nickName) {
        if (nickName == null || nickName.isEmpty()) {
            return null;
        }
        return qStudyTime.user.nickname.contains(nickName);
    }

    private BooleanExpression eqPeriod(String period) {
        if (period == null || period.isEmpty()) {
            return null;
        }
        int days = calculateDays(period);
        return qStudyTime.createdDate.between(LocalDateTime.now().minusDays(days).withHour(0).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now());

    }

    private BooleanExpression eqDepartment(long userPid, String department) {
        if (userPid == 0 || department == null || department.isEmpty()) {
            return null;
        }
        if (department.equals("department")) {
            User user = userRepository.findById(userPid);
            return qStudyTime.user.department.name.eq(user.getDepartment().getName());
        }
        if (department.equals("rival")) {
            List<Rival> rivalList = rivalRepository.findByUserId(userPid);
            List<Long> rivalIdList = new ArrayList<>();
            for (int i = 0; i < rivalList.size(); i++) {
                rivalIdList.add(rivalList.get(i).getRival().getId());
            }
            return qStudyTime.user.id.in(rivalIdList);
        }
        return null;
    }

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
