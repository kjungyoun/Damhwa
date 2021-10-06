package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.service.FlowerService;
import com.ssafy.damhwa.api.service.HistoryService;
import com.ssafy.damhwa.db.entity.Flower;
import com.ssafy.damhwa.db.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/flower")
public class FlowerController {

    @Autowired
    FlowerService flowerService;
    @Autowired
    HistoryService historyService;

    // 특정 Flower 상세 정보 조회
    @GetMapping("/detail")
    public ResponseEntity<Flower> getFlowerDetail(@RequestParam int fno){
        Flower flower = flowerService.getFlowerByfno(fno);

        return new ResponseEntity<Flower>(flower, HttpStatus.OK);
    }

    // 특정 User 전체 History List 조회
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistoryList(@RequestParam long userno){
        List<History> list = historyService.getHistoryList(userno);

        return new ResponseEntity<List<History>>(list, HttpStatus.OK);
    }
}
