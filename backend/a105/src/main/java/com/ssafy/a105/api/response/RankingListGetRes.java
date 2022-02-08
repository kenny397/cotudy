package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 랭킹 API ([GET] /api/v1/ranking) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("RankingListGetResponse")
public class RankingListGetRes {
    @ApiModelProperty(name="유저 닉네임", example="ssafy")
    String nickName;
    @ApiModelProperty(name="유저의 소속", example="SSAFY")
    String department;
    @ApiModelProperty(name="유저 총 공부 시간", example="390(6시간30분)")
    long totalStudyTime;

    public RankingListGetRes(){

    }

    @QueryProjection
    public RankingListGetRes(String nickName, String department, long totalStudyTime) {
        this.nickName = nickName;
        this.department = department;
        this.totalStudyTime = totalStudyTime;
    }

    public static RankingListGetRes of(String nickName, String department, long totalStudyTime) {
        RankingListGetRes res = new RankingListGetRes();
        res.setNickName(nickName);
        res.setDepartment(department);
        res.setTotalStudyTime(totalStudyTime);
        return res;
    }


}
