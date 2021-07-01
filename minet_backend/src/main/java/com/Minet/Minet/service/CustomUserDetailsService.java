package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
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
        User user = createUser(userid, member.get());
        return user;
    }

    private User createUser(String username, Member member) {
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(member.getAuthority().toString()));

        return new User(member.getEmail(), member.getPassword(), grantedAuthority);
    }
}
