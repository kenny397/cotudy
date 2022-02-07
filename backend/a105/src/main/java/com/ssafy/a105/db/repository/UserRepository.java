package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface UserRepository extends JpaRepositoryImplementation<User,Long> {
    List<User> findByUserId(String id);

}
