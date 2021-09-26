package com.ssafy.damhwa.api.service;

import org.springframework.http.ResponseEntity;

public interface DjangoService {

    ResponseEntity<String> getMsgRecommendFlower(String msg);
    ResponseEntity<Long> getStateRecommendFlower (String msg);
}
