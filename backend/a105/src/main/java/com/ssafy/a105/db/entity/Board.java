package com.ssafy.a105.db.entity;

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
public class Board extends BaseEntity{
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    User writer;

    String content;
    LocalDateTime createdDate;
    int views;

    @ManyToOne(fetch = FetchType.LAZY)
    BoardClass boardClass;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "board")
    Collection<Comment> comment;
}
