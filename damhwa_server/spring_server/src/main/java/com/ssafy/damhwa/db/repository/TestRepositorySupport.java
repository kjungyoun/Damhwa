package com.ssafy.damhwa.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.damhwa.db.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestRepositorySupport {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    // JPA에서 지원하지 않는 Repository Method 추가
}
