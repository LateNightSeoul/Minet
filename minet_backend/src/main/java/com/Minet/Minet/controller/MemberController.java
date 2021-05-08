package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.TokenDto;
import com.Minet.Minet.dto.member.JoinArtistDto;
import com.Minet.Minet.dto.member.JoinMemberDto;
import com.Minet.Minet.dto.member.LoginDto;
import com.Minet.Minet.jwt.JwtFilter;
import com.Minet.Minet.jwt.TokenProvider;
import com.Minet.Minet.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private MemberService memberService;

    @PostMapping("/join/member")
    public ResponseEntity<Member> join(
            @RequestBody JoinMemberDto joinMemberDto
            ) {
        return ResponseEntity.ok(memberService.joinMember(joinMemberDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserid(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

    }

}
