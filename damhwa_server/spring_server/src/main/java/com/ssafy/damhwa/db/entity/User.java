package com.ssafy.damhwa.db.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class User {
//    @GeneratedValue(strategy = GenerationType.IDENTITY) -> 이것을 사용하면 DB가 ID값 자동 생성 (Auto Increment)
    @Id
    Long userno;
    @Column
    String email;
    @Column
    String username;
    @Column
    String profile;

    @Builder
    public User(Long userno, String email, String username, String profile) {
        this.userno = userno;
        this.email = email;
        this.username = username;
        this.profile = profile;
    }

    public User update(String username, String profile){
        this.username = username;
        this.profile = profile;
        return this;
    }
}
