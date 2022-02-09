package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.CheckRes;
import com.ssafy.a105.api.response.RoomInfoRes;
import com.ssafy.a105.api.response.UserRes;
import com.ssafy.a105.api.service.UserService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ApiOperation(value = "회원 등록", notes = "받은 정보를 가지고 회원가입을 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerUser(@RequestBody  UserRegisterPostReq registerInfo){
        User user = userService.createUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<? extends BaseResponseBody> registerUser(@PathVariable("userId") long userId){

        userService.deleteUser(userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<? extends BaseResponseBody> modifyUser(@RequestBody  UserRegisterPostReq registerInfo/*수정할때 받는 DTO로 변경 필요*/){
       User user = userService.modifyUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> getUserInfo(@PathVariable("userId") long userId){
        User user = userService.getUserInfo(userId);

        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    @GetMapping("/check/id/{userId}")
    public ResponseEntity<CheckRes> checkEmail(@PathVariable("userId") String email){
        long count = userService.getCheckEmail(email);
        return ResponseEntity.status(200).body(CheckRes.of(count));
    }

    @GetMapping("/check/nickName/{userId}")
    public ResponseEntity<CheckRes> checkNickName(@PathVariable("userId") String nickName){
        long count= userService.getCheckNickName(nickName);
        return ResponseEntity.status(200).body(CheckRes.of(count));
    }

    //@GetMapping("/rival/{userId}")

    //@GetMapping("/time/today/{userId}")

    //@GetMapping("/time/entire/{userId}")




}
