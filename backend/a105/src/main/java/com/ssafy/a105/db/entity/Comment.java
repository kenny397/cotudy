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
public class Comment extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String content;
    private LocalDateTime createdDate;

    @PrePersist
    public void createdAt(){
        this.createdDate = LocalDateTime.now();
    }
}
