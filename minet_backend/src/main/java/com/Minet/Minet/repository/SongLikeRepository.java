package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.statistic.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongLikeRepository extends JpaRepository<SongLike, String> {
    List<SongLike> findByMember(Member member);
}
