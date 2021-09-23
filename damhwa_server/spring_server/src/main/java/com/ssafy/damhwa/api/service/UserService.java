package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.User;

import java.util.Optional;

public interface UserService {
    boolean createUser(User user);
    Optional<User> findUserByNo(long userno);
    boolean updateUser(User user);
}
