package com.ssafy.a105.api.response;

import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Rival Res")
public class RivalRes {
    @ApiModelProperty(name="라이벌유저 pk", example="1")
    long id;
    @ApiModelProperty(name="라이벌유저 이메일정보", example="ssafy@ssafy.com")
    String userId;
    @ApiModelProperty(name="라이벌 유저 닉네임", example="라이벌임돠")
    String userNickName;

    public static RivalRes of(User rival){
        RivalRes rivalRes =new RivalRes();
        rivalRes.setId(rival.getId());
        rivalRes.setUserId(rival.getUserId());
        rivalRes.setUserNickName(rival.getNickname());
        return rivalRes;
    }
}
