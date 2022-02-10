package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RivalRepository extends JpaRepository<Rival,Long> {
    List<Rival> findByUserId(long id);

    Rival findByUserIdAndRivalId(long userId, long rivalId);
}
