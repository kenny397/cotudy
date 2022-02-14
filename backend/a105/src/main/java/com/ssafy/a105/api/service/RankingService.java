package com.ssafy.a105.api.service;


import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingService {
    Page<RankingListGetRes> listRanking(RankingListDto rankingInfo, Pageable pageable);
    RankingRankGetRes getUserRankInfo(long userPid);
}
