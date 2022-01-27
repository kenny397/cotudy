package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Comment extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    User writer;

    String content;
    LocalDateTime createdDate;
}
