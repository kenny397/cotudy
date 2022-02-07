package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.StudyTime;
import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface StudyTimeRepository extends JpaRepositoryImplementation<StudyTime,Long> {
    StudyTime save(StudyTime studyTime);
    List<StudyTime> findByUser(User user);
}
