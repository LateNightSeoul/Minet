package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findOneByEmail(userId);
    }
}
