package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(MemberType memberType, String email, String password, String memberName, Role role, String refreshToken, LocalDateTime tokenExpirationTime) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.role = role;
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public static Member createMember(Member member) {
        return Member.builder()
                .memberType(member.getMemberType())
                .email(member.getEmail())
                .password(member.getPassword())
                .memberName(member.getMemberName())
                .role(member.getRole())
                .build();
    }

    public void updateRefreshTokenAndExpirationTime(TokenDto tokenDto) {
        this.refreshToken = tokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(tokenDto.getAccessTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }



}