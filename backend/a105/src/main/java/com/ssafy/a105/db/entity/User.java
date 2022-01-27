package com.ssafy.a105.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

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
    Collection<UserRoom> userRoom;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "owner") // 한명의 유저가 여러방의 방장 가능...?
    Collection<Room> room;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    Collection<Board> board;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    Collection<Comment> comment;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "user") // 한명의 유저가 여러명의 유저...?
    Collection<Rival> user;
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "rival")
    Collection<Rival> rival;

    @OneToMany(mappedBy = "user")
    Collection<StudyTime> studyTime;



}
