package com.Minet.Minet.jwt;

import com.Minet.Minet.dto.member.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private final MemberDto memberDto;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, MemberDto memberDto) {
        super(username, password, authorities);
        this.memberDto = memberDto;
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, MemberDto memberDto) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.memberDto = memberDto;
    }

    public CustomUser(MemberDto memberDto, String password, Collection<? extends GrantedAuthority> authorities) {
        super(null, password, authorities);
        this.memberDto = memberDto;
    }
}
