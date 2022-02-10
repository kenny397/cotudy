package com.ssafy.a105.api.service;


import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingService {

    //public List<RankingListGetRes> listRanking(RankingListGetReq rankingInfo);
    public Page<RankingListGetRes> listRanking(RankingListDto rankingInfo, Pageable pageable);
    //public Page<RankingListGetRes2> listRanking( Pageable pageable);
    //public Page<RankingListGetRes> listRanking(Pageable pageable);
    //public List<RankingListGetRes> listRanking();
}
