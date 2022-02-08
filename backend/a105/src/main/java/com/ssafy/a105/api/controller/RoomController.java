package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.request.RoomEnterPostReq;
import com.ssafy.a105.api.request.RoomExitPostReq;
import com.ssafy.a105.api.request.RoomInfoPostReq;
import com.ssafy.a105.api.response.RoomInfoRes;
import com.ssafy.a105.api.service.RoomService;
import com.ssafy.a105.api.service.RoomServiceImpl;
import com.ssafy.a105.common.BaseResponseBody;
import com.ssafy.a105.db.entity.Room;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping()
    @ApiOperation(value = "방 목록 조회", notes = "방 목록을 전부 조회한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<RoomInfoRes>> getRooms(){
        List<Room> rooms =roomService.getRooms();
        System.out.println(rooms);
       // if(rooms.isEmpty())return ResponseEntity.status(500).body(null);
        List<RoomInfoRes> roomsInfo =new ArrayList<>();
        for(int i=0; i<rooms.size(); i++){
            roomsInfo.add(RoomInfoRes.of(/*roomService.getRoomHeadCount(rooms.get(i).getId())*/1,rooms.get(i)));
        }
        return ResponseEntity.status(200).body(roomsInfo);
    }

    @GetMapping("/{room_id}")
    @ApiOperation(value = "방 정보를 가져온다",notes = "room_id 인 정보를 가져온다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<RoomInfoRes> getRoom(@PathVariable("room_id") long roomId){
        Room room = roomService.getRoom(roomId);
        long count = roomService.getRoomHeadCount(roomId);
        return ResponseEntity.status(200).body(RoomInfoRes.of(count,room));
    }

    @DeleteMapping("/{room_id}")
    @ApiOperation(value = "방을 종료한다.",notes = "room_id인 방을 db에서 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteRoom(@PathVariable("room_id")int roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"방이 삭제되었습니다."));
    }

    @PostMapping()
    @ApiOperation(value = "방을 생성한다",notes = "body에 있는 정보를 가지고 방을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createRoom(@RequestBody RoomInfoPostReq roomInfo){
        System.out.println(roomInfo);
        Room room = roomService.createRoom(roomInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"방이 생성되었습니다."));
    }

    @PostMapping("/exit")
    @ApiOperation(value = "방을 나가고 공부시간을 저장한다",notes = "body에 있는 정보를 통해서 시간테이블에 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends  BaseResponseBody> exitRoom(@RequestBody RoomExitPostReq studyInfo){
        roomService.exitRoom(studyInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"공부시간이 생성되었습니다."));
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방에 입장한다.",notes = "유저 정보와 방정보를 가지고 db에 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends  BaseResponseBody> enterRoom(@RequestBody RoomEnterPostReq roomInfo){
        roomService.enterRoom(roomInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"방 정보가 저장되었습니다."));
    }


    /*@GetMapping("/{room_id}/headcount")
    @ApiOperation(value = "방 인원수 조회.",notes = "방 인원수를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<HashMap<String,Object>> getRoomHeadCount(@PathVariable("room_id") int roomId){
        HashMap<String, Object>roomHeadCount = roomService.getRoomHeadCount(roomId);
        return ResponseEntity.status(200).body(roomHeadCount);

    }*/

}
