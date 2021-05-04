package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {

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

}
