package com.ssafy.a105.api.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyTimeRes {
    long id;
    String userId;
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
