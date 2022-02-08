package com.ssafy.a105.db.repository;

<<<<<<< HEAD
public interface UserRepository {
=======
import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUserId(String id);

>>>>>>> 846046aa71dbca15014fd925ac877abe4c5fc72f
}
