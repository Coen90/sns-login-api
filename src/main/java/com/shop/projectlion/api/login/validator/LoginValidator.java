package com.shop.projectlion.api.login.validator;

import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.global.error.exception.AuthenticationException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.error.exception.InvalidValueException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginValidator {

    public void validateAuthorization(String authorizationHeader) {

        // 1. authorization 필수 체크
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorization Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARRER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

    public void validateMemberType(String memberType) {
        if(!MemberType.isMemberType(memberType)) {
            throw new InvalidValueException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }
}
