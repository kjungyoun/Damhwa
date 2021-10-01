package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.User;

import java.util.Optional;

public interface UserService {
    void createOrUpdateUser(User user);
    Optional<User> findUserByNo(long userno);
}
