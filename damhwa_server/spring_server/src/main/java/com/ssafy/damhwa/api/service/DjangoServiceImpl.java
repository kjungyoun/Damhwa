package com.ssafy.damhwa.api.service;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
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
    public ResponseEntity<Integer[]> getMsgRecommendFlower(String msg) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/api/recomm/msg";

        // header 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 데이터 셋팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("msg",msg);

        // header와 이터요청 데 결합하여 요청 생성
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        System.out.println("==================== Django call (request) : " + msg + " ======================");
        ResponseEntity<Integer[]> response = restTemplate.postForEntity(url,request, Integer[].class);
        Integer[] result = response.getBody(); // 응답 받은 fno 출력
        System.out.println("==================== Django end ==========================");

        // 응답 확인
        System.out.print("reponse : ");
        for (int i=0; i<result.length; i++){
            System.out.print(" " + result[i]);
        }
        System.out.println();

        // 응답 받은 fno로 flower db 조회

        return response;
    }

    @Override
    public ResponseEntity<Long> getStateRecommendFlower(String msg) {
        return null;
    }
}
