package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.StudyTime;

import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudyTimeRepository extends JpaRepository<StudyTime,Long> {
    StudyTime save(StudyTime studyTime);
    List<StudyTime> findByUser(User user);

}
