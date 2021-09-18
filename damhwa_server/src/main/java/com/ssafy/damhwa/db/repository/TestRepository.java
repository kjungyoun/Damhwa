package com.ssafy.damhwa.db.repository;

import com.ssafy.damhwa.db.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    // Test 용 Repository

    Test findByContents(String contents);
}
