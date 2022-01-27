package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class UserRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Room room;

//    public static UserRoom of (User user, Room room){
//        UserRoom userRoom = new UserRoom();
//        UserRoom.setUser(user);
//        UserRoom.setRoom(room);
//        return userRoom;
//    }

}
