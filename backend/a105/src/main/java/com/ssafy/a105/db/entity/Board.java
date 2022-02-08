package com.ssafy.a105.db.entity;

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
public class Board extends BaseEntity{
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String content;
    private LocalDateTime createdDate;
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardClass boardClass;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();
    // @PrePersist -> insert 연산시 같이 실행되게하는 annotation
    @PrePersist
    public void createdAt(){
        this.createdDate = LocalDateTime.now();
    }
}
