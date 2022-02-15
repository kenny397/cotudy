package com.ssafy.a105.db.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("RankingListDto")
public class RankingListDto {

    @ApiModelProperty(name="라이벌 또는 내 소속", example="rival or department")
    String userClass;
    @ApiModelProperty(name="기간(하루,일주일,한달)", example="day or week or month")
    String term;
    @ApiModelProperty(name="카테고리", example="language 외 5개")
    String category;
    @ApiModelProperty(name="검색 닉네임", example="열정맨")
    String userNickname;
    @ApiModelProperty(name="로그인 유저 PK", example="50")
    long userId;

    public static RankingListDto of(String userClass,
                                    String term,
                                    String category,
                                    String userNickname,
                                    long userId){
        RankingListDto body = new RankingListDto();
        body.userClass = userClass;
        body.term = term;
        body.category = category;
        body.userNickname = userNickname;
        body.userId = userId;
        return body;
    }
}
