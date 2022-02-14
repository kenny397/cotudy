package com.ssafy.a105.api.service;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import com.ssafy.a105.db.repository.StudyTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService{
    private final StudyTimeRepository studyTimeRepository;

    @Override
    public Page<RankingListGetRes> listRanking(RankingListDto rankingInfo, Pageable pageable) {
        Page<RankingListGetRes> rankings = studyTimeRepository.getListByUser(rankingInfo, pageable);
        return rankings;
    }

    @Override
    public RankingRankGetRes getUserRankInfo(long userPid){
       RankingRankGetRes userInfo = studyTimeRepository.getUserRankInfoByUser(userPid);
        return userInfo;
    }

}
