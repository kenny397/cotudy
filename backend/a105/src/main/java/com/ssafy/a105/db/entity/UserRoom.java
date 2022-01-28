package com.ssafy.a105.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString
public class UserRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

//    public static UserRoom of (User user, Room room){
//        UserRoom userRoom = new UserRoom();
//        UserRoom.setUser(user);
//        UserRoom.setRoom(room);
//        return userRoom;
//    }

}
