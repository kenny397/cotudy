package com.ssafy.a105.api.request;

import com.ssafy.a105.db.entity.StudyClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ApiModel("RoomInfoRequest")
public class RoomInfoPostReq {
    @ApiModelProperty(name="방 제목", example="자격증 공부방")
    String roomTitle;
    @ApiModelProperty(name="방 최대인원 ", example="8")
    int roomMaxPeople;
    @ApiModelProperty(name="방 설명", example="자격증 방입니다. 26세 이상만 들어와주세요 ")
    String roomDescription;
    @ApiModelProperty(name="방 썸네일", example="1")
    String roomThumbnail;
    @ApiModelProperty(name="방 카테고리", example="1")
    String roomCategory;
}
