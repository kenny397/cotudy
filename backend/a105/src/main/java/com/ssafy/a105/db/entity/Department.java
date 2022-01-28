package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Department extends BaseEntity{
    String name;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "department")
    List<User> user = new ArrayList<>();
}
