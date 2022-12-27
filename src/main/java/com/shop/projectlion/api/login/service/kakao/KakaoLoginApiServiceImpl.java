package com.shop.projectlion.api.login.service.kakao;

import com.shop.projectlion.api.health.dto.OAuthAttributes;
import com.shop.projectlion.api.login.service.SocialLoginApiService;
import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.member.constant.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoFeignClient kakaoFeignClient;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String PROPERTY_NICKNAME = "nickname";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {

        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getUserInfo(CONTENT_TYPE, GrantType.BEARRER.getType() + " " + accessToken);

        String email = kakaoUserInfo.getKakaoAccount().getEmail();

        // 서비스에 연결하는 시점의 카카오계정 프로필을 복사하여 기본으로 제공 => 실시간 프로필만 제공하는 정책으로 변경됨
        Map<String, String> properties = kakaoUserInfo.getProperties();
        String nickname = properties.get(PROPERTY_NICKNAME);

        nickname = kakaoUserInfo.getKakaoAccount().getProfile().getNickname(); // 실시간 프로필에 있는 닉네임 조회

        return OAuthAttributes.builder()
                .email(StringUtils.hasText(email) ? email : kakaoUserInfo.getId())
                .name(nickname)
                .memberType(MemberType.KAKAO)
                .build();
    }
}
