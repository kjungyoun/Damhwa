package com.ssafy.damhwa.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.damhwa.db.entity.QUser;
import com.ssafy.damhwa.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    @Autowired
    UserRepository userRepository;

    public long findUsernoByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        long userno = -1;

        if(user.isPresent()){
            userno = user.get().getUserno();
        }

        return userno;
    }
}
