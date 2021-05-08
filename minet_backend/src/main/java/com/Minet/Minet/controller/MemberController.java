package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Member;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/hi")
    public ResponseEntity<Member> info2(){
        Member user = new Member();
        user.setUserName("이해석");
        user.setUserId("leehae8");
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/hi")
    public List<Member> find() {
        Member member = new Member();
        member.setUserName("이해석");
        Member member2 = new Member();
        member2.setUserName("최준혁");


        return Arrays.asList(member, member2);
    }

    @PostMapping("/join")
    public HttpStatus createMember(@RequestBody Map<String, String> param){
        Member member = new Member();
        member.setUserId(param.get("id"));
        member.setPassword(passwordEncoder.encode(param.get("password")));

        memberService.creatMember(member);
        return HttpStatus.OK;
    }

}
