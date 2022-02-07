package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RankingListGetReq;
import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.db.repository.StudyTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService{

    final StudyTimeRepository studyTimeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RankingListGetRes> listRanking(RankingListGetReq rankingInfo, Pageable pageable) {
        //Page<RankingListGetRes> rankings = studyTimeRepository.findAll(pageable);
        return null;
    }
}
