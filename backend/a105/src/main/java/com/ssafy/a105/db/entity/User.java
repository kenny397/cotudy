package com.ssafy.a105.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class User extends BaseEntity{
    String userId;
    String nickname;
    String goalTime;
    String goal;
    String thumbnail;
    LocalDateTime createdDate;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;



}
