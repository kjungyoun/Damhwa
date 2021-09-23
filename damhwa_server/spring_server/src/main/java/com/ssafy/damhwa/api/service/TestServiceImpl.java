package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.Test;
import com.ssafy.damhwa.db.repository.TestRepository;
import com.ssafy.damhwa.db.repository.TestRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;

public class TestServiceImpl implements TestService{

    @Autowired
    TestRepositorySupport testRepositorySupport;

    @Autowired
    TestRepository testRepository;

    @Override
    public void create(String contents) {
        Test test = new Test();
        test.setContents(contents);
         testRepository.save(test);
    }

    @Override
    public void update(long id, String contents) {
//        Test test = testRepository.findById(id).get();
//        test.update(contents);
//        testRepository.save(test);
    }
}
