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
public class Board extends BaseEntity{
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    User writer;

    String content;
    LocalDateTime createdDate;
    @Column(columnDefinition = "INT UNSIGNED")
    int views;

    @ManyToOne(fetch = FetchType.LAZY)
    BoardClass boardClass;
}
