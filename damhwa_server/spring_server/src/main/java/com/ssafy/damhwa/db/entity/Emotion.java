package com.ssafy.damhwa.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int fno;
    double happy;
    double unstable;
    double embarrass;
    double sad;
    double angry;
    double hurt;
    String emotion;
}
