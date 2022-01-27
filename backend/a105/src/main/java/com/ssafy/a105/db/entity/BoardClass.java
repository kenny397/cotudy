package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
public class BoardClass extends BaseEntity{
    String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "boardClass")
    Collection<Board> board;
}
