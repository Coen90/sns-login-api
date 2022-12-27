package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.exception.TokenNotFoundException;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.domain.member.validator.MemberValidator;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public void registerMember(Member member) {
        memberValidator.validateDuplicateMember(member.getEmail());
        memberRepository.save(member);
    }

    public void validateRegisterMember(Member member) {
        memberValidator.validateDuplicateMember(member.getEmail());
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        return member;
    }

}
