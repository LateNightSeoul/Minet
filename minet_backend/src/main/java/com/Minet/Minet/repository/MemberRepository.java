package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveMember(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findMember(Long id) {
        return em.find(Member.class, id);
    }
}