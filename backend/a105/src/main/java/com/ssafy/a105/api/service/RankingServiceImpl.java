package com.ssafy.a105.api.service;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.db.dto.RankingListDto;
import com.ssafy.a105.db.repository.RankingListRepository;
import com.ssafy.a105.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService{

    private final RankingListRepository rankingListRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RankingListGetRes> listRanking(RankingListDto rankingInfo, Pageable pageable) {
        Page<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging(rankingInfo, pageable);
        return rankings;
    }

    @Override
    public RankingRankGetRes getUserRankInfo(long userPid){
        RankingRankGetRes userInfo = rankingListRepository.getUserRankInfoByUser(userPid);
        return userInfo;
    }

}
