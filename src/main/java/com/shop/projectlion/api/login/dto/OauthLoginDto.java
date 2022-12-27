package com.shop.projectlion.api.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.member.constant.MemberType;
import lombok.*;

import java.util.Date;

public class OauthLoginDto {

    @Getter
    @Setter
    public static class Request {
        private String memberType;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String grantType;
        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static OauthLoginDto.Response of(TokenDto tokenDto) {
            return Response.builder()
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshToken(tokenDto.getRefreshToken())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }

    }
}
