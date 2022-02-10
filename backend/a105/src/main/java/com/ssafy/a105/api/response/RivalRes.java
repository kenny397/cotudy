package com.ssafy.a105.api.response;

import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RivalRes {
    long id;
    String userId;
    String UserNickName;

    public static RivalRes of(User rival){
        RivalRes rivalRes =new RivalRes();
        rivalRes.setId(rival.getId());
        rivalRes.setUserId(rival.getUserId());
        rivalRes.setUserNickName(rival.getNickname());
        return rivalRes;
    }
}
