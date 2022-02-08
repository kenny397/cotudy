package com.ssafy.a105.db.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RankingListRepositoryTest {

    @Autowired
    RankingListRepository rankingListRepository;

//    @Test
//    void querydsl_페이징처리() {
//
//        Sort.Order order = Sort.Order.desc("totalStudyTime");
//        Sort sort = Sort.by(order);
//
//        Pageable pageable = PageRequest.of(0, 5, sort);
//        //when
//        List<RankingListGetRes> result = rankingListRepository.getTotalStudyTimeByUserPaging();
//
//        //then
//    }

}