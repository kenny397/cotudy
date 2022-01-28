package com.ssafy.a105.db.entity;

import lombok.Getter;
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
public class BoardClass extends BaseEntity{
    private String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "boardClass")
    private List<Board> board = new ArrayList<>();
}
