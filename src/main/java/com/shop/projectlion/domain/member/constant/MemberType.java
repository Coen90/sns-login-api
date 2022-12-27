package com.shop.projectlion.domain.member.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MemberType {

    GENERAL, KAKAO;

    public static boolean isMemberType(String type) {
        List<MemberType> collect = Arrays.stream(MemberType.values())
                .filter(memberType -> !memberType.equals("GENERAL"))
                .filter(memberType -> memberType.name().equals(type))
                .collect(Collectors.toList());

        return collect.size() != 0;
    }

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase());
    }

}
