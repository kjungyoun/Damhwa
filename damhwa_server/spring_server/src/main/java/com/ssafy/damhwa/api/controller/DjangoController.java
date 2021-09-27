package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.service.DjangoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/recomm")
public class DjangoController {

    @Autowired
    DjangoService djangoService;

    @GetMapping("/msg")
    public ResponseEntity<Integer[]> msgRecomm (@RequestParam String msg){

        ResponseEntity<Integer[]> response = djangoService.getMsgRecommendFlower(msg);

        return response;
    }

}
