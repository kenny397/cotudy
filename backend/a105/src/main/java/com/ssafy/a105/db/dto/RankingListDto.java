package com.ssafy.a105.db.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RankingListDto {

    String keyDepartment;
    String keyPeriod;
    String keyCategory;
    String searchUserNickname;
    long loginUserPid;

    public static RankingListDto of(String keyDepartment,
                                    String keyPeriod,
                                    String keyCategory,
                                    String searchUserNickname,
                                    long loginUserPid){
        RankingListDto body = new RankingListDto();
        body.keyDepartment = keyDepartment;
        body.keyPeriod = keyPeriod;
        body.keyCategory = keyCategory;
        body.searchUserNickname = searchUserNickname;
        body.loginUserPid = loginUserPid;
        return body;
    }
}
