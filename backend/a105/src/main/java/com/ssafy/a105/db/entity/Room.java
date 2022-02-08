package com.ssafy.a105.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room extends BaseEntity{
    private String title;
    private LocalDateTime createdDate;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private String thumbnail;
    private int maxUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyClass studyClass;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "room")
    private List<UserRoom> userRoom = new ArrayList<>();

    @PrePersist
    public void createdAt(){
        this.createdDate = LocalDateTime.now();
    }
}
