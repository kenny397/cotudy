package com.ssafy.a105.api.request;

import com.ssafy.a105.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRequest")
public class UserPostReq {
    @ApiModelProperty(name="User PK")
    long id;
    @ApiModelProperty(name="User ID")
    String userId;
    @ApiModelProperty(name="User nickName")
    String nickName;
    @ApiModelProperty(name="department PK")
    long departmentId;
    @ApiModelProperty(name="User goalTime")
    String goalTime;
    @ApiModelProperty(name="User goal")
    String goal;
    @ApiModelProperty(name="User thumbnail")
    String thumbnail;

}

