package com.shop.projectlion.web.kakaotoken;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    private final KakaoTokenClient kakaoTokenClient;

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String GRANT_TYPE = "authorization_code";
    private final String REDIRECT_URI = "http://3.37.124.189/auth/kakao/callback";

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String loginCallback(String code) {

        // todo 카카오 토큰 조회 및 반환 로직 구현
        KakaoTokenResponseDto kakaoToken = kakaoTokenClient.requestKakaoToken(
                CONTENT_TYPE, GRANT_TYPE, clientId, REDIRECT_URI, code, clientSecret
        );

        return "kakao token : " + kakaoToken;
    }

}
