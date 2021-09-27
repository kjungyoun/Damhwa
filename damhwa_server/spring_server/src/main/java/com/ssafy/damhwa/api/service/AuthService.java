package com.ssafy.damhwa.api.service;

import com.ssafy.damhwa.db.entity.User;

public interface AuthService {

    String getAccessToken(String authorizationToken); // kakao AccessToken 발급 Method
    User getUserInfo(String accessToken); // AccessToken으로 카카오 User 정보 조회
}
