package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserid(Member member);
    Member findByUsername(String username);
}
