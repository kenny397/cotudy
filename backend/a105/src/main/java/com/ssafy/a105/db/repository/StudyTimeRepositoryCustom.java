package com.ssafy.a105.db.repository;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface StudyTimeRepositoryCustom {
    RankingRankGetRes getUserRankInfoByUser(long userPid);
    PageImpl<RankingListGetRes> getListByUser(RankingListDto rankingInfoPageable,Pageable pageable);
}
