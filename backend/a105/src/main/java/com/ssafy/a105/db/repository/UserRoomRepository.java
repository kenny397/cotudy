package com.ssafy.a105.db.repository;


import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom,Long> {
    UserRoom save(UserRoom userRoom);
    UserRoom findByRoomIdAndUserId(long roomId, long userId);
    void delete(UserRoom userRoom);
  //  List<UserRoom>findByRoom(Room room);
    List<UserRoom> findByRoomId(long roomId);
}
