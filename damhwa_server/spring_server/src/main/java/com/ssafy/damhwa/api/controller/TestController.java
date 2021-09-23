package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.request.TestReq;
import com.ssafy.damhwa.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController // REST API 선언
@RequestMapping("/test")
public class TestController {

    // Controller 구현
    @GetMapping("/main")
    public void main (@RequestBody TestReq testReq){
        String contents = testReq.getTestContents();

    }
}
