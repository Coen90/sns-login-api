package com.shop.projectlion.api.health.dto;

import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role, TokenDto tokenDto) {
        return Member.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .role(role)
                .refreshToken(tokenDto.getRefreshToken())
                .tokenExpirationTime(DateTimeUtils.convertToLocalDateTime(tokenDto.getRefreshTokenExpireTime()))
                .build();
    }
}
