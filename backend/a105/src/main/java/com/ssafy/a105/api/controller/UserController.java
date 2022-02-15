package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.request.RivalPostReq;
import com.ssafy.a105.api.request.UserPostReq;
import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.*;
import com.ssafy.a105.api.service.UserService;
import com.ssafy.a105.common.model.response.BaseResponseBody;
import com.ssafy.a105.db.dto.AttendanceDateDto;
import com.ssafy.a105.db.dto.UserRivalDto;
import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.Room;
import com.ssafy.a105.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value="유저 API" , tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final String YES = "yes";
    private static final String NO = "no";

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디어와 패스워드</strong>를 가지고 회원가입을 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerUser(@RequestBody  UserRegisterPostReq registerInfo){

        User user = userService.createUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "회원 삭제", notes = "<strong>회원의 유저 PK</strong>를 가지고 회원탈퇴를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteUser(@PathVariable("userId") long userId){

        userService.deleteUser(userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "회원 수정", notes = "현재 회원 정보 형식을 정하지 않아 email password nickname만 변경가능" +
            "<strong>회원의 유저 정보</strong>를 가지고 회원정보 수정 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> modifyUser(@RequestBody UserPostReq userInfo){
       User user = userService.modifyUser(userInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "회원 정보 조회", notes = "현재 회원정보를 email만 반환함" +
            "<strong>회원의 유저 PK</strong>를 가지고 회원정보를 조회 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserRes> getUserInfo(@PathVariable("userId") long userId){
        User user = userService.getUserInfo(userId);

        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    @GetMapping("/check/id/{userId}")
    @ApiOperation(value = "이메일 중복 조회", notes = "<strong>입력 받은 email</strong>를 가지고 회원중복 조회를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CheckRes> checkEmail(@PathVariable("userId") String email){
        long count = userService.getCheckEmail(email);
        return ResponseEntity.status(200).body(CheckRes.of(count));
    }

    @GetMapping("/check/nickName/{userId}")
    @ApiOperation(value = "닉네임 중복 조회", notes = "<strong>입력 받은 닉네임</strong>를 가지고 회원중복를 조회를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CheckRes> checkNickName(@PathVariable("userId") String nickName){
        long count= userService.getCheckNickName(nickName);
        return ResponseEntity.status(200).body(CheckRes.of(count));
    }

    @GetMapping("/rival/{userId}")
    @ApiOperation(value = "라이벌 조회", notes = "<strong>회원의 유저 PK</strong>를 가지고 라이벌들을 조회 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<RivalRes>> getRivals(@PathVariable("userId") long id){
        List<Rival> rivals = userService.getRivals(id);
        if(rivals.isEmpty())return ResponseEntity.status(500).body(null);
        List<RivalRes> rivalResList=new ArrayList<>();
        for(int i=0; i<rivals.size(); i++){
            rivalResList.add(RivalRes.of(rivals.get(i).getRival()));
        }
        return ResponseEntity.status(200).body(rivalResList);

    }
    @PostMapping("/rival")
    @ApiOperation(value = "라이벌 등록", notes = "<strong>회원의 정보와 라이벌 정보</strong>를 가지고 라이벌 등록를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerRival(@RequestBody RivalPostReq rivalPostReq) {
        userService.registerRival(rivalPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"라이벌 등록 완료."));
    }
    @DeleteMapping("/rival")
    @ApiOperation(value = "라이벌 삭제", notes = "<strong>회원의 정보와 라이벌 정보</strong>를 가지고 라이벌삭제를 한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteRival(@RequestBody RivalPostReq rivalPostReq) {
        userService.deleteRival(rivalPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"라이벌 삭제 완료."));
    }

    @GetMapping("/rival")
    @ApiOperation(value = "해당 유저의 라이벌인지 조회", notes = "<strong>유저2의 PK</strong>가 <strong>사용자의 PK</strong>의 라이벌인지 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> checkRival(UserRivalDto userRivalDto){
        if (userService.isRival(userRivalDto) == 0){
            return ResponseEntity.status(200).body(NO);
        }
        return ResponseEntity.status(200).body(YES);

    }

    @GetMapping("/time/entire/{userId}")
    @ApiOperation(value = "전체 스터디 시간 조회", notes = "<strong>회원의 유저 PK</strong>를 가지고 전체 스터디 시간을 조회준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<StudyTimeRes> getTotalStudyTime(@PathVariable("userId") long id){
        StudyTimeRes studyTimeRes= userService.getTotalStudyTime(id);
        return ResponseEntity.status(200).body(studyTimeRes);
    }

    @GetMapping("/time/today/{userId}")
    @ApiOperation(value = "오늘 스터디 시간 조회", notes = "<strong>회원의 유저 PK</strong>를 가지고 오늘 스터디 시간을 조회한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<StudyTimeRes> getDayStudyTime(@PathVariable("userId") long id){
       StudyTimeRes studyTimeRes= userService.getDayStudyTime(id);
        return ResponseEntity.status(200).body(studyTimeRes);
    }

    @GetMapping("/calendar")
    @ApiOperation(value = "해당 유저의 출석일 반환", notes = "<strong>회원의 유저 PK</strong>와 년/월을 가지고 해당 기간의 출석일을 반환한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<CalendarRes>> getAttendanceDays(AttendanceDateDto attendanceDateDto){
        List<CalendarRes> output = userService.getAttendanceDays(attendanceDateDto);
        return ResponseEntity.status(200).body(output);
    }


}
