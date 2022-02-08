package com.ssafy.a105.api.service;


import com.ssafy.a105.api.response.RankingListGetRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingService {

    //public Page<RankingListGetRes> listRanking(RankingListGetReq rankingInfo, Pageable pageable);
    //public List<RankingListGetRes> listRanking(RankingListGetReq rankingInfo);
    public Page<RankingListGetRes> listRanking(Pageable pageable);
    //public List<RankingListGetRes> listRanking();
}
