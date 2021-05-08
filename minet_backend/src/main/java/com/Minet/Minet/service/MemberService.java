package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

//    public boolean findByUsername(Member member) {
//        memberRepository.findByUsername(member);
//    }

    public void creatMember(Member member) {
        memberRepository.save(member);
        Member findMember = memberRepository.findByUserid(member);

    }
}
