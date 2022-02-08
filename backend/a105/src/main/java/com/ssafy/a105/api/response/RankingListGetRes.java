package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberExpression;
import com.ssafy.a105.db.entity.QUser;
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
    @ApiModelProperty(name="유저 아이디", example="ssafy12")
    String userId;
    @ApiModelProperty(name="유저 닉네임", example="ssafy")
    String nickName;
    @ApiModelProperty(name="유저의 소속", example="SSAFY")
    String studyClass;
    @ApiModelProperty(name="유저 총 공부 시간", example="390(6시간30분)")
    int totalStudyTime;

    public RankingListGetRes(){

    }

    @QueryProjection
    public RankingListGetRes(String userId, int totalStudyTime) {
        this.userId = userId;
        this.totalStudyTime = totalStudyTime;
    }

    public RankingListGetRes(QUser user, NumberExpression<Integer> sum) {
    }

    public static RankingListGetRes of(String nickName, String studyClass, int totalStudyTime) {
        RankingListGetRes res = new RankingListGetRes();
        res.setNickName(nickName);
        res.setStudyClass(studyClass);
        res.setTotalStudyTime(totalStudyTime);
        return res;
    }


}
