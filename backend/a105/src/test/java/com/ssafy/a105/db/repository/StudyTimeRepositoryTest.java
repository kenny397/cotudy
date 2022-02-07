package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.StudyTime;
import com.ssafy.a105.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class StudyTimeRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    StudyTimeRepository studyTimeRepository;

    @Test
    public void 시간저장테스트() throws Exception {
        //given
        User user = new User();
        user.setUserId("testid");
        user.setPassword("1234");
        user.setNickname("test");
        entityManager.persist(user);
        StudyTime studyTime= new StudyTime();
        studyTime.setUser(user);
        studyTime.setTime("123");


        StudyTime s=studyTimeRepository.save(studyTime);

        List<StudyTime> getStudyTime= studyTimeRepository.findByUser(user);

        //when
        assertEquals(getStudyTime.get(0),studyTime);
        //then

    }

}