package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.api.response.FlowerNEmotionRes;
import com.ssafy.damhwa.db.entity.Flower;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DjangoService {

    List<Flower> getMsgRecommendFlower(String msg);
    Flower getStateRecommendFlower (String state);
}
