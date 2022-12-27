package com.shop.projectlion.api.login.service;

import com.shop.projectlion.api.health.dto.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);

}
