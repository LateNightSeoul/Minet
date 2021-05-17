package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.JoinDto;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.security.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback(false)
    public void 회원가입_아티스트() throws Exception {

        JoinDto joinDto = JoinDto.builder()
                .userid("leehae2")
                .artistName("선스톤")
                .password(passwordEncoder.encode("1234"))
                .authority(Authority.ROLE_ARTIST)
                .build();

        Member memberJoined = authService.join(joinDto);
        Artist artist = memberJoined.getArtist();

        assertEquals(artist.getMember().getUserid(), memberJoined.getUserid());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 회원가입_일반() throws Exception {

        JoinDto joinDto = JoinDto.builder()
                .userid("leehae3")
                .password(passwordEncoder.encode("1234"))
                .authority(Authority.ROLE_MEMBER)
                .build();

        Member memberJoined = authService.join(joinDto);

        assertEquals(joinDto.getUserid(), memberJoined.getUserid());
    }

}