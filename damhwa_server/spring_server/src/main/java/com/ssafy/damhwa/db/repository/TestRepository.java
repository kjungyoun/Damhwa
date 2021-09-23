package com.ssafy.damhwa.db.repository;

import com.ssafy.damhwa.db.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    // Test 용 Repository

    Test findByContents(String contents);
    Optional<Test> findById(Long id);
}
