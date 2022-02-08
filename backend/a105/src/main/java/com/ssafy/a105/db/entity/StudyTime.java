package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class StudyTime extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyClass studyClass;


    private Long time;

    private LocalDateTime createdDate;

    @PrePersist
    public void createdAt(){
        this.createdDate = LocalDateTime.now();
    }

}
