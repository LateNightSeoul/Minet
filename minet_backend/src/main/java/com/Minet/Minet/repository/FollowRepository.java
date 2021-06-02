package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.statistic.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByMember(Member member);
}
