package com.ssafy.damhwa.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.damhwa.db.entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Kakao API를 이용한 Auth 작업 Service
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserService userService;

    public final static String kakaoRestApiKey = "6bd2a7e8dba6b9e9ab76edcda34eec65";
    public final static String redirectUri = "http://localhost:8080/auth/kakao/login";

    @Override
    // AccessToken 요청
    public String getAccessToken(String authorizationToken) {
        String reqUrl = "https://kauth.kakao.com/oauth/token"; // AccessToken 요청 url

        List<NameValuePair> postParams = new ArrayList<NameValuePair>(); // 요청 파라미터로 보낼 List

        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", kakaoRestApiKey));
        postParams.add(new BasicNameValuePair("redirect_uri", redirectUri));
        postParams.add(new BasicNameValuePair("code", authorizationToken));

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(reqUrl); // 요청 생성 (Post 방식 요청)

        String accessToken = "";
        JsonNode resultNode = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams)); // 요청 url에 요청 파라미터 셋팅

            HttpResponse response = client.execute(post); // 요청 수행 후 응답 저장
            int responseCode = response.getStatusLine().getStatusCode(); // 응답 코드 확인

            System.out.println("\nSending 'POST' request to URL : " + reqUrl);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code(GetAccessToken) : " + responseCode);

            ObjectMapper mapper = new ObjectMapper();
            resultNode = mapper.readTree(response.getEntity().getContent());

            System.out.println("Response Body(GetAccessToken) : " + resultNode);
            System.out.println("AccessToken : " + resultNode.get("access_token").toString());
            System.out.println("RefreshToken : " + resultNode.get("refresh_token").toString());

            accessToken = resultNode.get("access_token").toString();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    @Override
    public User getUserInfo(String accessToken) {
        User user = new User(); // 가져온 UserInfo를 저장할 객체
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        try{
            // 요청 URL 생성
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header 설정
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode(); // 응답 코드 확인
            System.out.println("Response Code(GetInfo) : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 응답 결과를 받아옴

            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("Response Body(GetKakaoInfo) : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            // kakao Info에서 필요한 정보 추출
            long userno = element.getAsJsonObject().get("id").getAsLong();
            String username = properties.getAsJsonObject().get("nickname").getAsString();
            String profile = properties.getAsJsonObject().get("profile_image").getAsString();
            boolean hasEmail = kakao_account.getAsJsonObject().get("has_email").getAsBoolean();
            String email = null;

            // email이 존재하는지 확인 (User 선택 정보)
            if(hasEmail){
                email = kakao_account.getAsJsonObject().get("email").getAsString();
            }

            user.setEmail(email);
            user.setUserno(userno);
            user.setUsername(username);
            user.setProfile(profile);

            // Kakao User가 이미 존재하는 회원인지 확인
            Optional<User> dbUser = userService.findUserByNo(userno);
            boolean isSuccess;

            if(dbUser.isPresent()) // 이미 존재하는 User
                isSuccess = userService.updateUser(user); // User 정보 최신화
            else
                isSuccess = userService.createUser(user); // User 생성

            if(isSuccess){
                System.out.println("GetUserInfo : " + user);
                return user;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
