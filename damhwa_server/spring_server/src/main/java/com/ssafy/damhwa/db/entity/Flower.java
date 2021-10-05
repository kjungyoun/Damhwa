package com.ssafy.damhwa.db.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int fno;

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
    @Column
    String watercolor_img;
    @Transient
    String emotionResult;
}
