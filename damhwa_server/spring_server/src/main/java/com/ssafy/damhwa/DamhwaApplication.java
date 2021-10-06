package com.ssafy.damhwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class DamhwaApplication {

    // 실행 초기 한번만 실행 (Timezone 설정)
    @PostConstruct
    public void start(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DamhwaApplication.class, args);
    }

}
