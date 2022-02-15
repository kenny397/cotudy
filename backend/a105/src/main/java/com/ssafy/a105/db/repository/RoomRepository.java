package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findById(long roomId);
    Room save(Room room);
    void delete(Room room);
    List<Room> findAll();
}
