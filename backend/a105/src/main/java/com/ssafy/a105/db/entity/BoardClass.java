package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class BoardClass extends BaseEntity{
    String name;
}
