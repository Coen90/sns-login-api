package com.shop.projectlion.api.login.service.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoClient")
public interface KakaoFeignClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfo getUserInfo(
            @RequestHeader("Content-type") String contentType,
            @RequestHeader("Authorization") String accessToken
    );
}
