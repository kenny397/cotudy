package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RoomEnterPostReq;
import com.ssafy.a105.api.request.RoomExitPostReq;
import com.ssafy.a105.api.request.RoomInfoPostReq;
import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.repository.RoomRepository;
import com.ssafy.a105.db.repository.StudyTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final StudyTimeRepository studyTimeRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room getRoom(long roomId) {
        return roomRepository.findById(roomId);
    }
    @Transactional
    public void deleteRoom(long roomId) {
        Room room = roomRepository.findById(roomId);
        roomRepository.delete(room);
        return;
    }

    @Transactional
    public Room createRoom(RoomInfoPostReq roomInfo) {
        Room room = new Room();
        room.setTitle(roomInfo.getRoomTitle());
        room.setMaxUser(roomInfo.getRoomMaxPeople());
        room.setDescription(roomInfo.getRoomDescription());
        room.setThumbnail(roomInfo.getRoomThumbnail());
        //카테고리 어떻게 할지 못정함
        //룸만들기
        return roomRepository.save(room);
    }

    public void exitRoom(RoomExitPostReq studyInfo) {
        //user_room 데이터 까기
        //study_time 테이블 주기
    }

    public void enterRoom(RoomEnterPostReq roomInfo) {
        //user_room 데이터 추가?
    }

    public Long getRoomHeadCount(long roomId) {
        //user_room 개수 조회
        return null;
    }
}
