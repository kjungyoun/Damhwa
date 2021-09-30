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

    // User 생성 또는 Update
    @Override
    public void createOrUpdateUser(User user) {
        Optional<User> dbUser = userRepository.findByUserno(user.getUserno());
        if(!dbUser.isPresent()) { // 만약 존재하지 않는 User이면
            System.out.println("==============유저 생성================ : " + user);
            userRepository.save(user);
        }else{
            System.out.println("==============유저 수정================ : " + user);
           User updateUser =  dbUser.get().update(user.getUsername(), user.getProfile(), user.getEmail());
           userRepository.save(updateUser);
        }
    }

    // User 조회
    @Override
    public Optional<User> findUserByNo(long userno) {
        return userRepository.findByUserno(userno);
    }
}
