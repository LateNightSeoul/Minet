package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.JoinArtistDto;
import com.Minet.Minet.dto.member.JoinMemberDto;
import com.Minet.Minet.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join/member")
    public ResponseEntity<Member> join(
            @RequestBody JoinMemberDto joinMemberDto
            ) {
        return ResponseEntity.ok(memberService.joinMember(joinMemberDto));
    }

}
