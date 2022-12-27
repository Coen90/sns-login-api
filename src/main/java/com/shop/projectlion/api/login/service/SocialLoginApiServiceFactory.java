package com.shop.projectlion.api.login.service;

import com.shop.projectlion.domain.member.constant.MemberType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialApiServices) {
        this.socialApiServices = socialApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType) {
        String socialApiServiceBeanName = "";

        if(MemberType.KAKAO.equals(memberType)) {
            socialApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        SocialLoginApiService socialLoginApiService = socialApiServices.get(socialApiServiceBeanName);
        return socialLoginApiService;
    }

}
