package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("StudyTimeRes")
public class StudyTimeRes {
    @ApiModelProperty(name="유저 PK정보", example="1")
    long id;
    @ApiModelProperty(name="유저 이메일", example="ssafy@ssafy.com")
    String userId;
    @ApiModelProperty(name="공부시간", example="6시간 30분은 390으로 표현")
    long studyTime;

    public StudyTimeRes(){

    }
    @QueryProjection
    public StudyTimeRes(long id , String userId, long studyTime){
        this.id=id;
        this.userId=userId;
        this.studyTime=studyTime;
    }
    public static StudyTimeRes of (long id , String userId, long studyTime){
        StudyTimeRes res = new StudyTimeRes();
        res.setId(id);
        res.setUserId(userId);
        res.setStudyTime(studyTime);
        return res;
    }

}
