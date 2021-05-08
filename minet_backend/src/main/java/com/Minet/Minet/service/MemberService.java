package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.JoinMemberDto;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.security.Authority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Member joinMember(JoinMemberDto joinMemberDto) {
        if(memberRepository.findOneByUserid(joinMemberDto.getUserid()) != null) {
            throw new RuntimeException("이미 가입된 id입니다.");
        }

        Member member = Member.builder()
                .username(joinMemberDto.getUsername())
                .userid(joinMemberDto.getUserid())
                .password(passwordEncoder.encode(joinMemberDto.getPassword()))
                .phone(joinMemberDto.getPhone())
                .enabled(true)
                .createTime(LocalDateTime.now())
                .role(Authority.ROLE_ARTIST)
                .build();

        return memberRepository.save(member);
    }
}
