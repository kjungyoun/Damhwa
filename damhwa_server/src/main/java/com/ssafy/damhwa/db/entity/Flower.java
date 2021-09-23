package com.ssafy.damhwa.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fno;

    @Column
    String fname_KR;
    @Column
    String fname_EN;
    @Column
    int fmonth;
    @Column
    int fday;
    @Column
    String flang;
    @Column
    String fcontents;
    @Column
    String fuse;
    @Column
    String fgrow;
    @Column
    String img1;
    @Column
    String img2;
    @Column
    String img3;

    @Builder

    public Flower(Long fno, String fname_KR, String fname_EN, int fmonth, int fday, String flang, String fcontents, String fuse, String fgrow, String img1, String img2, String img3) {
        this.fno = fno;
        this.fname_KR = fname_KR;
        this.fname_EN = fname_EN;
        this.fmonth = fmonth;
        this.fday = fday;
        this.flang = flang;
        this.fcontents = fcontents;
        this.fuse = fuse;
        this.fgrow = fgrow;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }
}
