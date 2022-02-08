package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudyClass extends BaseEntity {
    private String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "studyClass")
    private List<Room> room = new ArrayList<>();

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "studyClass")
    private List<StudyTime> studyTime = new ArrayList<>();

}
