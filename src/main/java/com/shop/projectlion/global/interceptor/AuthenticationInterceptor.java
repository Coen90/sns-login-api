package com.shop.projectlion.global.interceptor;

import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.jwt.constant.TokenType;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.global.error.exception.AuthenticationException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuthenticationInterceptor.preHandle");

        // 1. 토큰 유무 확인
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. Bearer Grant Type 확인
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || !GrantType.BEARRER.getType().equals(authorizations[0])) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

        String token = authorizations[1];

        // 3. 토큰 검증
        if(!tokenManager.validateToken(token)) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }

        String tokenType = tokenManager.getTokenType(token);

        // 4. 토큰 타입 검증
        if(!TokenType.ACCESS.name().equals(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 5. 엑세스 토큰 만료 시간 검증
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        Date expiration = tokenClaims.getExpiration();
        if(tokenManager.isTokenExpired(expiration)) {
            throw new AuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AuthenticationInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AuthenticationInterceptor.afterCompletion");
    }
}
