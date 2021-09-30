package com.ssafy.damhwa.api.service;


import com.ssafy.damhwa.api.response.FlowerNEmotionRes;
import com.ssafy.damhwa.api.response.StateRes;
import com.ssafy.damhwa.db.entity.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DjangoServiceImpl implements DjangoService {

    @Autowired
    FlowerService flowerService;

    @Autowired
    HistoryService historyService;

    @Override
    public List<Flower> getMsgRecommendFlower(String msg, String email, String receiver) {
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://j5a503.p.ssafy.io:8000/api/recomm/msg";
        String url = "http://localhost:8000/api/recomm/msg";

        // header 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 데이터 셋팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("msg",msg);

        // header와 요청데이터 결합하여 요청 생성
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        System.out.println("==================== Django call (request) : " + msg + " ======================");
        ResponseEntity<int[]> response = restTemplate.postForEntity(url,request, int[].class);
        int[] result = response.getBody(); // 응답 받은 fno 출력
        System.out.println("==================== Django end ==========================");

        // 응답 확인
        System.out.print("reponse : ");
        for (int i=0; i<result.length; i++){
            System.out.print(" " + result[i]);
        }
        System.out.println();

        // 응답 받은 fno로 flower db 조회
        List<Flower> flowers = flowerService.getFlowersByfno(result);

        // history에 결과 저장
        historyService.createMsgHistoryList(result, email, msg, receiver);


        return flowers;
    }

    @Override
    public FlowerNEmotionRes getStateRecommendFlower(String state, String email) {
        RestTemplate restTemplate = new RestTemplate();

        //String url = "http://j5a503.p.ssafy.io:8000/api/recomm/state";
        String url = "http://localhost:8000/api/recomm/state";

        // Header 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Request Data 셋팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("state", state);

        // Header와 요청 데이터 결합하여 요청 객체 생성
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map, headers);

        System.out.println("==================== Django call (request) : " + state + " ======================");
        ResponseEntity<StateRes> response = restTemplate.postForEntity(url,request, StateRes.class);
        System.out.println("==================== Django end (response) : " + response.getBody()+ " ==========================");

        int fno = response.getBody().getFno();
        String emotionResult = response.getBody().getState();


        // Flower DB 조회
        Flower flower = flowerService.getFlowerByfno(fno);

        // History에 검색 결과 저장
        historyService.createStateHistory(fno, email, state);

        FlowerNEmotionRes flowerNEmotionRes = new FlowerNEmotionRes();
        flowerNEmotionRes.setFlower(flower);
        flowerNEmotionRes.setEmotionResult(emotionResult);

        return flowerNEmotionRes;
    }
}
