package com.ssafy.damhwa.api.service;

import org.springframework.http.ResponseEntity;

public interface DjangoService {

    ResponseEntity<Integer[]> getMsgRecommendFlower(String msg);
    ResponseEntity<Long> getStateRecommendFlower (String msg);
}
