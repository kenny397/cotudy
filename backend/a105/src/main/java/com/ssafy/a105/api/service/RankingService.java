package com.ssafy.a105.api.service;


import com.ssafy.a105.api.request.RankingListGetReq;
import com.ssafy.a105.api.response.RankingListGetRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingService {

    public Page<RankingListGetRes> listRanking(RankingListGetReq rankingInfo, Pageable pageable);
}
