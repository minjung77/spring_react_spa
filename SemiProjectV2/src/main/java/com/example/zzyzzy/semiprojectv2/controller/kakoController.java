package com.example.zzyzzy.semiprojectv2.controller;

import com.example.zzyzzy.semiprojectv2.domain.KakaoTokenResponse;
import com.example.zzyzzy.semiprojectv2.domain.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

// 교차출처 리소스 공유 CORS
@CrossOrigin(origins={"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth/kakao")
public class kakoController {

    @Value("${kakao.client.id}") private String clientId;
    @Value("${kakao.redirect.uri}") private String redirectUri;

    private final RestTemplate restTemplate;
    private static String AccessToken;
    
    // 카카오 로그인 페이지
    // 카카오 로그인 후 인가코드 받기
    @GetMapping("/login")
    public String kakaoLogin() {
        String authorizeUrl = "https://kauth.kakao.com/oauth/authorize";
        String params = "?client_id=%s&redirect_uri=%s&response_type=code";
        String kakaoUrl = String.format(authorizeUrl+params, clientId, redirectUri);

        return "redirect:" + kakaoUrl;
    }
    
    // 카카오 인증 후 redirect 엔드포인트 - 인가 코드를 이용해서 엑세스 토큰 받기
    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
        // 1단계 : 인가 코드 출력 ( 테스트 용 )
        log.info("카카오 인가 코드 : {}" , code);

        // 2단계 : 엑세스 토큰 요청
        String authorizeUrl = "https://kauth.kakao.com/oauth/token";
        String params = "?client_id=%s&redirect_uri=%s&code=%s&grant_type=authorization_code";
        String kakaoUrl = String.format(authorizeUrl+params, clientId, redirectUri, code);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            // POST 요청으로 토큰 받기
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    kakaoUrl,
                    request,
                    KakaoTokenResponse.class
            );

            // 응답에서 엑세스 토큰 추출
            KakaoTokenResponse tokenResponse = response.getBody();
            if (tokenResponse != null) {
                AccessToken = tokenResponse.getAccess_token();
                log.info("AccessToken : {}", AccessToken);
            }
            //사용자 정보 가져오기
            KakaoUserInfo kakaoUserInfo = getUserInfo(AccessToken);
            log.info("kakaoUserinfo : {}", kakaoUserInfo);

            // 응답으로 닉네임 출력
            return ResponseEntity.ok().body(kakaoUserInfo.getProperties().getNickname());
        } catch (Exception e) {
            log.error("Error getting token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting token: " + e.getMessage());
        }

//        return null;
    }

    // 엑세스 토큰으로 사용자 정보 알아내기
    private KakaoUserInfo getUserInfo(String accessToken) {
        // 사용자 정보 요청을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(headers);

        // GET 요청으로 사용자 정보 받기
        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                KakaoUserInfo.class
        );

        return response.getBody();
    }

    // 카카오 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> kakaoLogout() {
        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + AccessToken);

        System.out.println("Using token: " + AccessToken);
        System.out.println("Authorization header: " + headers.get("Authorization"));

        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(headers);

        // GET 요청으로 사용자 정보 받기
        ResponseEntity<String> response = restTemplate.exchange(
                logoutUrl, HttpMethod.POST,
                request, String.class
        );

        log.info("Logout Response: {}", response.getStatusCode());

        return ResponseEntity.ok("로그아웃 성공!!");
    }
}
