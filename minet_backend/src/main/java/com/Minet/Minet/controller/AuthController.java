package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.AuthenticationDto;
import com.Minet.Minet.dto.member.JoinDto;
import com.Minet.Minet.dto.member.LoginDto;
import com.Minet.Minet.jwt.CustomUser;
import com.Minet.Minet.jwt.JwtFilter;
import com.Minet.Minet.jwt.TokenProvider;
import com.Minet.Minet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Member> join(@RequestBody JoinDto joinDto) {
        return ResponseEntity.ok(authService.join(joinDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserid(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUser customUser = (CustomUser)authentication;
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new AuthenticationDto(jwt, customUser.getMemberDto()), httpHeaders, HttpStatus.OK);
    }
}
