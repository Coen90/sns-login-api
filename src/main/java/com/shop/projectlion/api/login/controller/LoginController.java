package com.shop.projectlion.api.login.controller;

import com.shop.projectlion.api.login.dto.AccessTokenResponseDto;
import com.shop.projectlion.api.login.dto.OauthLoginDto;
import com.shop.projectlion.api.login.service.LoginService;
import com.shop.projectlion.api.login.validator.LoginValidator;
import com.shop.projectlion.domain.member.constant.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final LoginValidator loginValidator;

    @PostMapping("/oauth/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        loginValidator.validateAuthorization(authorizationHeader);
        loginValidator.validateMemberType(oauthLoginRequestDto.getMemberType());

        String accessToken = authorizationHeader.split(" ")[1];
        MemberType memberType = MemberType.from(oauthLoginRequestDto.getMemberType());
        OauthLoginDto.Response jwtTokenResponseDto = loginService.loginOauth(accessToken, memberType);

        return ResponseEntity.ok(jwtTokenResponseDto);
    }


    @PostMapping(value = "/token")
    public ResponseEntity<AccessTokenResponseDto> accessToken(HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        loginValidator.validateAuthorization(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        AccessTokenResponseDto accessTokenResponseDto = loginService.createAccessTokenByRefreshToken(refreshToken, LocalDateTime.now());

        return ResponseEntity.ok(accessTokenResponseDto);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        loginValidator.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        loginService.logout(accessToken);

        return ResponseEntity.ok().body("logout success");
    }
}
