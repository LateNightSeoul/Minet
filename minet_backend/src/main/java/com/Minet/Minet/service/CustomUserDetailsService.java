package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.MemberDto;
import com.Minet.Minet.jwt.CustomUser;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findOneByEmail(userid);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("데이터베이스에서 찾을 수 없습니다.");
        }
        
        return createUser(userid, member.get());
    }

    private CustomUser createUser(String username, Member member) {
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(member.getAuthority().toString()));

        return new CustomUser(createMemberDto(member), member.getPassword(), grantedAuthority);
    }

    private MemberDto createMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .userName(member.getUserName())
                .email(member.getEmail())
                .enabled(member.isEnabled())
                .createTime(member.getCreateTime())
                .loginCount(member.getLoginCount())
                .authority(member.getAuthority())
                .build();
    }
}
