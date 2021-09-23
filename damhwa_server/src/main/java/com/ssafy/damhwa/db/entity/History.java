package com.ssafy.damhwa.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hno;
    @Column
    Long fno;
    @Column
    Long userno;
    @Column
    Boolean htype;
    @Column
    String contents;
    @Column
    String to;
    @Column
    Date regdate = Calendar.getInstance().getTime(); // 현재 시간 Default

//    @ManyToOne
//    @JoinColumn(name = "userno",referencedColumnName = "userno",insertable = false, updatable = false)
//    User user;
}
