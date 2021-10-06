package com.ssafy.damhwa.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hno;
    int fno;
    Long userno;
    Boolean htype;
    String contents;
    String receiver;
    @Builder.Default
    Date regdate=Calendar.getInstance().getTime();

//    @ManyToOne
//    @JoinColumn(name = "userno",referencedColumnName = "userno",insertable = false, updatable = false)
//    User user;

    @OneToOne
    @JoinColumn(name= "fno", referencedColumnName = "fno", insertable = false, updatable = false)
    Flower flower;
}
