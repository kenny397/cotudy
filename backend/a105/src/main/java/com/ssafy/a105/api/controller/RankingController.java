package com.ssafy.a105.api.controller;

import com.ssafy.a105.api.response.RankingListGetRes;
import com.ssafy.a105.api.response.RankingRankGetRes;
import com.ssafy.a105.api.service.RankingService;
import com.ssafy.a105.db.dto.RankingListDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 랭킹페이지 관련 API 요청 처리를 위한 컨트롤러 정의
 */
@Api(value = "랭킹페이지 API", tags = {"ranking-controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    @ApiOperation(value = "모든 회원에 대한 랭킹 페이지 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Page<RankingListGetRes>> makeRankingList(RankingListDto searchData,  @ApiParam(value = "페이지 정보.", required = true) @PageableDefault(size = 20) Pageable pageable){
        System.out.println("searchData" + searchData);
        Page<RankingListGetRes> rankingList = rankingService.listRanking(searchData, pageable);
        return ResponseEntity.status(200).body(rankingList);
    }

    @GetMapping("/rank")
    @ApiOperation(value = "회원에 대한 랭킹 정보 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<RankingRankGetRes> makeUserRank(@ApiParam(value = "유저 닉네임 아이디", required = true)@RequestParam(value = "userId", defaultValue="0") long userPid){
        RankingRankGetRes userInfo = rankingService.getUserRankInfo(userPid);
        return ResponseEntity.status(200).body(userInfo);
    }
}
