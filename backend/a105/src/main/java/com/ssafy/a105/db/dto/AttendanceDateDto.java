package com.ssafy.a105.db.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("AttendanceDateDto")
public class AttendanceDateDto {
    @ApiModelProperty(name="사용자 PK", example="50")
    long userId;
    @ApiModelProperty(name="년", example="2022")
    int year;
    @ApiModelProperty(name="월", example="2")
    int month;

    public static AttendanceDateDto of(long userId,
                                       int year,
                                       int month){
        AttendanceDateDto body = new AttendanceDateDto();
        body.userId = userId;
        body.year = year;
        body.month = month;
        return body;

    }
}
