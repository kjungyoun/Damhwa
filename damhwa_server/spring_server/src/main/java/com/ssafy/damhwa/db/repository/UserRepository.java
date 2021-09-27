package com.ssafy.damhwa.db.repository;

import com.ssafy.damhwa.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByUserno(long userno);
}
