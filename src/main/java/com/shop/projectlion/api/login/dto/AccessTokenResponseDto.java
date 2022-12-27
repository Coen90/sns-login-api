package com.shop.projectlion.api.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.projectlion.domain.jwt.constant.GrantType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class AccessTokenResponseDto {

    private String grantType;

    private String accessToken;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date accessTokenExpireTime;

    public static AccessTokenResponseDto of(String accessToken, Date accessTokenExpireTime) {
        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARRER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
