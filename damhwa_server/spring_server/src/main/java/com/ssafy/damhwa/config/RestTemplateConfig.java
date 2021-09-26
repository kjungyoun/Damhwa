package com.ssafy.damhwa.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    // Connection Pooling을 위한 Apache HttpClient 사용
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setReadTimeout(5000); // read timeout 읽기 시간 초과
        factory.setConnectTimeout(3000); // connection timeout 연결 시간 초과

        // Apache HttpComponents HttpClient
        HttpClient httpClient = HttpClientBuilder.create()
                                    .setMaxConnTotal(50) // Max Connection
                                    .setMaxConnPerRoute(20).build();
                                    // 각 호스트 (IP와 Port의 조합) 당 커넥션 풀에 생성 가능한 커넥션 수

        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 셋팅

        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }

}
