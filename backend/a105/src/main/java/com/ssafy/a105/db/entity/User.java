package com.ssafy.a105.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(columnDefinition = "INT UNSIGNED")
    int state;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "user")
    List<UserRoom> userRoom = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "owner") // 한명의 유저가 여러방의 방장 가능...?
    List<Room> room = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    List<Board> board = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    List<Comment> comment = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "user") // 한명의 유저가 여러명의 유저...?
    List<Rival> user = new ArrayList<>();
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "rival")
    List<Rival> rival = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<StudyTime> studyTime = new ArrayList<>();



}
