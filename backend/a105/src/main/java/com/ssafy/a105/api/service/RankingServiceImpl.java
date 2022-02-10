package com.ssafy.a105.api.service;

import com.ssafy.a105.api.response.RankingListGetRes;
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

//    @Override
//    public List<RankingListGetRes> listRanking() {
//        List<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging();
//        return rankings;
//    }

    @Override
    public Page<RankingListGetRes> listRanking(RankingListDto rankingInfo, Pageable pageable) {
        Page<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging(rankingInfo, pageable);
        return rankings;
    }

//    @Override
//    public Page<RankingListGetRes2> listRanking(Pageable pageable) {
//        Page<RankingListGetRes2> rankings = rankingListRepository.getTotalStudyTimeByUserPaging2(pageable);
//
//        return rankings;
//    }

//    @Override
//    public Page<RankingListGetRes> listRanking(Pageable pageable) {
//        Page<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging(pageable);
//        return rankings;
//    }
}
