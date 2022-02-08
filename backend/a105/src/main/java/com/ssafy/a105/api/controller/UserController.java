package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.CheckRes;
import com.ssafy.a105.api.response.RoomInfoRes;
import com.ssafy.a105.api.response.UserRes;
import com.ssafy.a105.common.model.response.BaseResponseBody;
import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    @PostMapping()
    @ApiOperation(value = "회원 등록", notes = "받은 정보를 가지고 회원가입을 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerUser(@RequestBody  UserRegisterPostReq registerInfo){
        //user 서비스 호출 User user = userService.createUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<? extends BaseResponseBody> registerUser(@PathVariable("userId") long userId){
        //user 서비스 호출
        //userService.deleteUser(userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<? extends BaseResponseBody> modifyUser(@RequestBody  UserRegisterPostReq registerInfo/*수정할때 받는 DTO로 변경 필요*/){
        //user 서비스 호출 User user = userService.modifyUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> getUserInfo(@PathVariable("userId")long userId){
        // userService.getUser(userId);
        //User user = userService.getUserInfo(userId);
        //return ResponseEntity.status(200).body(UserRes.of(user));
        return null;
    }

    @GetMapping("/check/id/{userId}")
    public ResponseEntity<CheckRes> checkEmail(@PathVariable("userId") String email){
        //userService 불러서 있는지 없는지 확인
        return null;
    }

    @GetMapping("/check/nickName/{userId}")
    public ResponseEntity<CheckRes> checkNickName(@PathVariable("userId") String nickName){
        //userService 불러서 있는지 없는지 확인
        return null;
    }

    //@GetMapping("/rival/{userId}")

    //@GetMapping("/time/today/{userId}")

    //@GetMapping("/time/entire/{userId}")




}
