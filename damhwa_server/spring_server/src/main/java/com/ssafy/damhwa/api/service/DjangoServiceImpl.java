package com.ssafy.damhwa.api.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class DjangoServiceImpl implements DjangoService {


    @Override
    public ResponseEntity<String> getMsgRecommendFlower(String msg) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/api/recomm/msg";

        // header 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 데이터 셋팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("msg",msg);

        // header와 요청 데이터 결합하여 요청 생성
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        System.out.println("==================== Django call (request) : " + msg + " ======================");
        ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);
        System.out.println("==================== Django end (response) : " + response + "==========================");
        return response;
    }

    @Override
    public ResponseEntity<Long> getStateRecommendFlower(String msg) {
        return null;
    }
}
