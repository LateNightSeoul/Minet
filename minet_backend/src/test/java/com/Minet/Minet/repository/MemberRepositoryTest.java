package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        Member member = new Member();
        member.setUserName("이해석");
        Member member2 = new Member();

        Long savedId = memberRepository.save(member);
        Member member1 = memberRepository.find(savedId);

        org.assertj.core.api.Assertions.assertThat(member1.getId()).isEqualTo(member.getId());
        org.assertj.core.api.Assertions.assertThat(member1.getUserName()).isEqualTo(member.getUserName());

    }
}