package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RoomEnterPostReq;
import com.ssafy.a105.api.request.RoomExitPostReq;
import com.ssafy.a105.api.request.RoomInfoPostReq;
import com.ssafy.a105.db.entity.Room;

import java.util.List;

public interface RoomService {
    public List<Room> getRooms();

    public Room getRoom(long roomId);

    public void deleteRoom(long roomId);

    public Room createRoom(RoomInfoPostReq roomInfo);

    public void exitRoom(RoomExitPostReq studyInfo);

    public void enterRoom(RoomEnterPostReq roomInfo) ;



    public Long getRoomHeadCount(long roomId);
}
