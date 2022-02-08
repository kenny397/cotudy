package com.ssafy.a105.db.repository;
import com.ssafy.a105.db.entity.StudyClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyClassRepositoryTest {

    @Autowired
    StudyClassRepository studyClassRepository;

    @Test
    public void add(){

        String[] arr = {"Language","Public","Employment","Certificate","Entrance","Etc","Police","Firefighter"};

        for(int i = 0; i<8; i++) {
            StudyClass studyClass = new StudyClass();
            studyClass.setName(arr[i]);
            StudyClass newSC = studyClassRepository.save(studyClass);
            String name = newSC.getName();
            assertEquals(arr[i], name);
        }

    }

}