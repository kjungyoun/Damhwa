package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.request.MsgRecommReq;
import com.ssafy.damhwa.api.request.StateRecommReq;
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
    public ResponseEntity<List<Flower>> msgRecomm (@RequestBody MsgRecommReq msgRecommReq){
        String msg = msgRecommReq.getMsg();
        List<Flower> result = djangoService.getMsgRecommendFlower(msg);

        return new ResponseEntity<List<Flower>>(result,HttpStatus.OK);
    }

    // 기분 기반 추천
    @PostMapping("/state")
    public ResponseEntity<Flower> stateRecomm (@RequestBody StateRecommReq stateRecommReq){
        String state = stateRecommReq.getState();
        long userno = stateRecommReq.getUserno();
        System.out.println(state);
        Flower response = djangoService.getStateRecommendFlower(state, userno);

        return new ResponseEntity<Flower>(response, HttpStatus.OK);
    }

}
