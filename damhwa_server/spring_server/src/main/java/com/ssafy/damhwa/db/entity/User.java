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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userno;
    @Column
    String email;
    @Column
    String username;
    @Column
    String profile;
    @Column
    String token;

    @Builder
    public User(Long userno, String email, String username, String profile, String token) {
        this.userno = userno;
        this.email = email;
        this.username = username;
        this.profile = profile;
        this.token = token;
    }
}
