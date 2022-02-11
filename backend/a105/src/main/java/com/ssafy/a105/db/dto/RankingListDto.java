package com.ssafy.a105.db.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RankingListDto {

    String userClass;
    String term;
    String category;
    String userNickname;
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
