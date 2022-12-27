package com.shop.projectlion.api.login.service;

import com.shop.projectlion.api.health.dto.OAuthAttributes;
import com.shop.projectlion.api.login.dto.AccessTokenResponseDto;
import com.shop.projectlion.api.login.dto.OauthLoginDto;
import com.shop.projectlion.domain.jwt.constant.TokenType;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.member.validator.MemberValidator;
import com.shop.projectlion.global.error.exception.AuthenticationException;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;
    private final MemberValidator memberValidator;

    @Transactional
    public OauthLoginDto.Response loginOauth(String accessToken, MemberType memberType) {

        // 소셜 회원 정보 조회
        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, memberType);

        // 토큰 생성
        TokenDto tokenDto;
        Optional<Member> optionalMember = memberService.findMemberByEmail(oAuthAttributes.getEmail());
        if(optionalMember.isEmpty()) { // 신규회원일 경우
            tokenDto = tokenManager.createTokenDto(oAuthAttributes.getEmail(), Role.ADMIN);
            Member oauthMember = oAuthAttributes.toMemberEntity(memberType, Role.ADMIN, tokenDto);
            memberService.registerMember(oauthMember);
        } else { // 기존 회원일 경우
            Member oauthMember = optionalMember.get();
            tokenDto = tokenManager.createTokenDto(oAuthAttributes.getEmail(), oauthMember.getRole());
            oauthMember.updateRefreshTokenAndExpirationTime(tokenDto);
        }

        return OauthLoginDto.Response.of(tokenDto);
    }

    private OAuthAttributes getSocialUserInfo(String accessToken, MemberType memberType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OAuthAttributes oAuthAttributes = socialLoginApiService.getUserInfo(accessToken);
        return oAuthAttributes;
    }

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken, LocalDateTime now) {
        Member member = memberService.findMemberByRefreshToken(refreshToken);
        memberValidator.validateRefreshTokenExpirationTime(member.getTokenExpirationTime(), now);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(member.getEmail(), member.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDto.of(accessToken, accessTokenExpireTime);
    }

    @Transactional
    public void logout(String accessToken) {
        // 1. access token 만료 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        Date accessTokenExpiration = tokenClaims.getExpiration();
        if(tokenManager.isTokenExpired(accessTokenExpiration)) {
            throw new AuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        // 2. 토큰 타입 검증
        String tokenType = tokenClaims.getSubject();
        if(!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 3. 로그인할 때 발행한 refresh token 만료 처리
        String email = tokenManager.getMemberEmail(accessToken);
        Member member = memberService.findMemberByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));
        member.expireRefreshToken(LocalDateTime.now());
    }
}
