package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RoomEnterPostReq;
import com.ssafy.a105.api.request.RoomExitPostReq;
import com.ssafy.a105.api.request.RoomInfoPostReq;
import com.ssafy.a105.db.entity.*;
import com.ssafy.a105.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final StudyTimeRepository studyTimeRepository;
    private final UserRoomRepository userRoomRepository;
    private final StudyClassRepository studyClassRepository;
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
        StudyClass studyClass=studyClassRepository.getById(Long.parseLong(roomInfo.getRoomCategory()));
        room.setStudyClass(studyClass);

        //카테고리 어떻게 할지 못정함
        //룸만들기
        return roomRepository.save(room);
    }
    @Transactional
    public void exitRoom(RoomExitPostReq studyInfo) {
        //user_room 데이터 까기

        Room room = roomRepository.getById(studyInfo.getRoomId());
        User user = userRepository.getById(studyInfo.getUserId());
        UserRoom userRoom = userRoomRepository.findByRoomIdAndUserId(studyInfo.getRoomId(),studyInfo.getUserId());
        userRoomRepository.delete(userRoom);
        StudyTime studyTime = new StudyTime();
        studyTime.setTime(studyInfo.getStudyTime());
        studyTime.setCreatedDate(LocalDateTime.now());
        studyTime.setUser(user);
        studyTimeRepository.save(studyTime);
        //study_time 테이블 주기
    }
    @Transactional
    public void enterRoom(RoomEnterPostReq roomInfo) {
        UserRoom userRoom = new UserRoom();
        User user = userRepository.getById(roomInfo.getUserId());
        Room room = roomRepository.getById(roomInfo.getRoomId());

        userRoom.setRoom(room);
        userRoom.setUser(user);
        userRoomRepository.save(userRoom);
        getRoomHeadCount(room.getId());
    }

    public Long getRoomHeadCount(long roomId) {
        List<UserRoom> userRooms=userRoomRepository.findByRoomId(roomId);
        //user_room 개수 조회
        if(userRooms.isEmpty()){
            return 0L;
        }
        long headCount =(long)userRooms.size();
        return headCount;
    }
}
