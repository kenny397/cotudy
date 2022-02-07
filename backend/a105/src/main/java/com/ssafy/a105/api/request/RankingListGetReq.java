package com.ssafy.a105.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 랭킹 페이지 목록 API ([GET] /api/v1/ranking) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RankingListGetRequest")
public class RankingListGetReq {
//    @ApiModelProperty(name="현재 페이지 번호", example="1")
//    int pageNum;
//    @ApiModelProperty(name="페이지당 리스트 개수", example="20")
//    int numPerPage;
//    @ApiModelProperty(name="페이지의 시작 리스트 번호", example="1")
//    int start;
    @ApiModelProperty(name="기간에 대한 조건(all,month,week,day)", example="all")
    String keyPeriod;
    @ApiModelProperty(name="소속에 대한 조건(all, SSAFY, SNU)", example="SSAFY")
    String keyClass;
    @ApiModelProperty(name="라이벌 검색 활성화(all, rival)", example="1")
    String keyRival;
    @ApiModelProperty(name="검색어", example="닉네임명")
    String word;








}
