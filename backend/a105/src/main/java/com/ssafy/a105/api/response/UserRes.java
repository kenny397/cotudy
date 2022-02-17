package com.ssafy.a105.api.response;


import com.ssafy.a105.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes {
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
	@ApiModelProperty(name="User rivalCount")
	long rivalCount;
	public static UserRes of(User user,long rivalCount) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setId(user.getId());
		res.setNickName(user.getNickname());
		res.setDepartmentId(user.getDepartment().getId());
		res.setGoalTime(user.getGoalTime());
		res.setGoal(user.getGoal());
		res.setThumbnail(user.getThumbnail());
		res.setRivalCount(rivalCount);
		return res;
	}
}
