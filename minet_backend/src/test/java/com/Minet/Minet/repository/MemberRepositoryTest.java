package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArtistRepository artistRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        Member member = new Member();
        member.setUserName("이해석");
        Member member2 = new Member();

        Long savedId = memberRepository.saveMember(member);
        Member member1 = memberRepository.findMember(savedId);

        org.assertj.core.api.Assertions.assertThat(member1.getId()).isEqualTo(member.getId());
        org.assertj.core.api.Assertions.assertThat(member1.getUserName()).isEqualTo(member.getUserName());

    }

    @Test
    @Transactional
    @Rollback(false)
    public void insertMemberArtist() throws Exception {
        Member member = new Member();
        Artist artist1 = null;
        member.setArtist(true);
        member.setUserName("최준혁");

        if(member.isArtist()) {
            Artist artist = new Artist();
            artist.setArtistName("최코모드");
            artist.setBirth(LocalDate.of(1995, 3, 2));
            Long artistId = artistRepository.saveArtist(artist);
            artist1 = artistRepository.findArtist(artistId);
            artist1.setMember(member);
        }

        Long memberId = memberRepository.saveMember(member);

        Member member1 = memberRepository.findMember(memberId);

        System.out.println("dddddddd" + artist1.getArtistName());

        Assertions.assertThat(member1.getId()).isEqualTo(artist1.getMember().getId());

    }
}