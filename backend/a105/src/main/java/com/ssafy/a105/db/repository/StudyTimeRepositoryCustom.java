package com.ssafy.a105.db.repository;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.api.response.UserRecentWeekStudyTimeRes;
import com.ssafy.a105.db.dto.RankingListDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyTimeRepositoryCustom {
    RankingRankGetRes getUserRankInfoByUser(long userPid);
    PageImpl<RankingListGetRes> getListByUser(RankingListDto rankingInfoPageable,Pageable pageable);
    List<UserRecentWeekStudyTimeRes> getStudyTimeWeekList(long id);
}
