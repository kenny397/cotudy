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
    String title;
    LocalDateTime createdDate;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    User owner;

    String thumbnail;
    int maxUser;

    @ManyToOne(fetch = FetchType.LAZY)
    StudyClass studyClass;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "room")
    List<UserRoom> userRoom = new ArrayList<>();
}
