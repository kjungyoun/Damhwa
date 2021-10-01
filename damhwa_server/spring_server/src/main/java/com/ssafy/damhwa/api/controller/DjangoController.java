package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.response.FlowerNEmotionRes;
import com.ssafy.damhwa.api.service.DjangoService;
import com.ssafy.damhwa.db.entity.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/recomm")
@CrossOrigin("*")
public class DjangoController {

    @Autowired
    DjangoService djangoService;

    // 메세지 기반 추천
    @PostMapping("/msg")
    public ResponseEntity<List<Flower>> msgRecomm (@RequestBody String msg){
        List<Flower> result = djangoService.getMsgRecommendFlower(msg);

        return new ResponseEntity<List<Flower>>(result,HttpStatus.OK);
    }

    // 기분 기반 추천
    @PostMapping("/state")
    public ResponseEntity<FlowerNEmotionRes> stateRecomm (@RequestBody String state){
        FlowerNEmotionRes response = djangoService.getStateRecommendFlower(state);

        return new ResponseEntity<FlowerNEmotionRes>(response, HttpStatus.OK);
    }

}
