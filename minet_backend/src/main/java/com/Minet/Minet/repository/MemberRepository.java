package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findOneByUserid(String userid);
    Member findOneByUsername(String username);
}
