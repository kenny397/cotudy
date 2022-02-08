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
    private String userId;
    private String nickname;
    private String goalTime;
    private String goal;
    private String thumbnail;
    private LocalDateTime createdDate;
    @Column(columnDefinition = "INT UNSIGNED")
    private int state;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "user")
    private List<UserRoom> userRoom = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "owner") // 한명의 유저가 여러방의 방장 가능...?
    private List<Room> room = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    private List<Board> board = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "writer")
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "user") // 한명의 유저가 여러명의 유저...?
    private List<Rival> user = new ArrayList<>();
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "rival")
    private List<Rival> rival = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudyTime> studyTime = new ArrayList<>();

    @PrePersist
    public void createdAt(){
        this.createdDate = LocalDateTime.now();
    }



}
