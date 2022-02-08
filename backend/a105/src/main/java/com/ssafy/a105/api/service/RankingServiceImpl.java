package com.ssafy.a105.api.service;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.db.repository.RankingListRepository;
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

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<RankingListGetRes> listRanking() {
//        List<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging();
//        return rankings;
//    }

    @Override
    public Page<RankingListGetRes> listRanking(Pageable pageable) {
        Page<RankingListGetRes> rankings = rankingListRepository.getTotalStudyTimeByUserPaging(pageable);
        return rankings;
    }
}
