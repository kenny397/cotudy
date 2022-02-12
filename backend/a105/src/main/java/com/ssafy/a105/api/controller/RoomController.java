package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.request.RoomEnterPostReq;
import com.ssafy.a105.api.request.RoomExitPostReq;
import com.ssafy.a105.api.request.RoomInfoPostReq;
import com.ssafy.a105.api.response.RoomInfoRes;
import com.ssafy.a105.api.response.RoomRegisterRes;
import com.ssafy.a105.api.service.RoomService;
import com.ssafy.a105.api.service.RoomServiceImpl;
import com.ssafy.a105.common.model.response.BaseResponseBody;
import com.ssafy.a105.db.entity.Room;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Api(value="룸 API" , tags = {"Rooms"})
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping()
    @ApiOperation(value = "전체 방정보 조회", notes = "<strong>전체 방정보를 </strong>조회를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "방 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<RoomInfoRes>> getRooms(){
        List<Room> rooms =roomService.getRooms();

        if(rooms.isEmpty())return ResponseEntity.status(500).body(null);
        List<RoomInfoRes> roomsInfo =new ArrayList<>();
        for(int i=0; i<rooms.size(); i++){
            roomsInfo.add(RoomInfoRes.of(roomService.getRoomHeadCount(rooms.get(i).getId()),rooms.get(i)));
        }
        return ResponseEntity.status(200).body(roomsInfo);
    }

    @GetMapping("/{room_id}")
    @ApiOperation(value = "해당 방정보 조회", notes = "<strong>방PK 번호를 가지고 방정보를 </strong>조회를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "방 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<RoomInfoRes> getRoom(@PathVariable("room_id") long roomId){
        Room room = roomService.getRoom(roomId);
        long count = roomService.getRoomHeadCount(roomId);
        return ResponseEntity.status(200).body(RoomInfoRes.of(count,room));
    }

    @DeleteMapping("/{room_id}")
    @ApiOperation(value = "방 삭제 ", notes = "<strong> 방을 </strong>삭제를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "방 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteRoom(@PathVariable("room_id")int roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"방이 삭제되었습니다."));
    }

    @PostMapping()
    @ApiOperation(value = "방 생성", notes = "<strong> 방정보를 </strong>가지고 방을 생성 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<RoomRegisterRes> createRoom(@RequestBody RoomInfoPostReq roomInfo){
        System.out.println(roomInfo);
        Room room = roomService.createRoom(roomInfo);

        return ResponseEntity.status(200).body(RoomRegisterRes.of(room.getId()));
    }

    @PostMapping("/exit")
    @ApiOperation(value = "방을 나가고 공부시간 저장", notes = "<strong>방을 나가고 공부시간 </strong>을 저장한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends  BaseResponseBody> exitRoom(@RequestBody RoomExitPostReq studyInfo){
        roomService.exitRoom(studyInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"공부시간이 생성되었습니다."));
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방을 입장", notes = "<strong>방을 입장해 현재 인원수를  </strong>증가한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
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
