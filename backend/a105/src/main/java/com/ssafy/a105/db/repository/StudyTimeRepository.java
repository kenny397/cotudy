package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.StudyTime;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyTimeRepository extends JpaRepository<StudyTime, Long> {
=======
import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface StudyTimeRepository extends JpaRepository<StudyTime,Long> {
    StudyTime save(StudyTime studyTime);
    List<StudyTime> findByUser(User user);
>>>>>>> 846046aa71dbca15014fd925ac877abe4c5fc72f
}
