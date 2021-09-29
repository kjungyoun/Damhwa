package com.ssafy.damhwa.api.controller;

import com.ssafy.damhwa.api.service.AuthService;
import com.ssafy.damhwa.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// Kakao Social Login Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class AuthController {

    @Autowired
    AuthService authService;

    // kakao 로그인 성공 후 redirect 되는 url -> AuthorizeToken을 넘겨 받음
    @GetMapping("/login")
    public ModelAndView getAuthorizationToken(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("kakao Authorization code : " + code);

        String accessToken = authService.getAccessToken(code); // AccessToken 요청
        User user =  authService.getUserInfo(accessToken); // Kakao Info 를 DB에 저장하여 User 정보 최신화

        String responseUrl = "http://localhost:8080/";
//        String responseUrl = "http://j5a503.p.ssafy.io:8080/";
        ModelAndView mav = new ModelAndView("redirect:" + responseUrl); // 특정 url로 redirect 시켜줌
        mav.addObject("userno", user.getUserno());
        mav.addObject("accessToken", accessToken);
        return mav;
    }

}
