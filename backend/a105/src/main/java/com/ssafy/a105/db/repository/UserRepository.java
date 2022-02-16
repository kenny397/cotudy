package com.ssafy.a105.db.repository;


import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {
    User findById(long userId);
    User save(User user);
    void delete(User user);
    Optional<User> findByUserId(String email);
    Optional<User> findByNickname(String nickName);


}
