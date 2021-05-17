package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOneByUserid(String userid);
    Optional<Member> findOneByUsername(String username);
}
