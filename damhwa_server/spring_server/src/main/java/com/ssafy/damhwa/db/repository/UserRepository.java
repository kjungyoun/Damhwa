package com.ssafy.damhwa.db.repository;

import com.ssafy.damhwa.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByUserno(long userno);
    Optional<User>findByEmail(String email);

}
