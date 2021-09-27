package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.User;
import com.ssafy.damhwa.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// User 관련 DB 작업 Service
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    // User 생성
    @Override
    public boolean createUser(User user) {
        System.out.println("==============유저 생성================ : " + user);
        if(!userRepository.findByUserno(user.getUserno()).isPresent()){ // 만약 존재하지 않는 User이면
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // User 조회
    @Override
    public Optional<User> findUserByNo(long userno) {
        return userRepository.findByUserno(userno);
    }

    // User 정보 갱신
    @Override
    public boolean updateUser(User user) {
        Optional<User> dbUser = userRepository.findByUserno(user.getUserno()); // 기존 User정보 조회

        if(!dbUser.isPresent()) return false; // 존재하지 않은 User면

        User updatedUser = dbUser.get().update(user.getUsername(),user.getProfile()); // 기존 User 정보 수정
        userRepository.save(updatedUser);
        return true;

    }
}
