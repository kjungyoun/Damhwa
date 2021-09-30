package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.request.MsgRecommendReq;
import com.ssafy.damhwa.api.request.StateRecommendReq;
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
    @GetMapping("/msg")
    public ResponseEntity<List<Flower>> msgRecomm (MsgRecommendReq msgRecommendReq){
        String msg = msgRecommendReq.getMsg();
        String email = msgRecommendReq.getEmail();
        String receiver = msgRecommendReq.getReceiver();
        List<Flower> result = djangoService.getMsgRecommendFlower(msg, email, receiver);

        return new ResponseEntity<List<Flower>>(result,HttpStatus.OK);
    }

    // 기분 기반 추천
    @GetMapping("/state")
    public ResponseEntity<FlowerNEmotionRes> stateRecomm (StateRecommendReq stateRecommendReq){
        String state = stateRecommendReq.getState();
        String email = stateRecommendReq.getEmail();

        FlowerNEmotionRes response = djangoService.getStateRecommendFlower(state, email);

        return new ResponseEntity<FlowerNEmotionRes>(response, HttpStatus.OK);
    }

}
