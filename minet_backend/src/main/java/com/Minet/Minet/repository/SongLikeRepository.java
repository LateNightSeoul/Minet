package com.Minet.Minet.repository;

import com.Minet.Minet.domain.statistic.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongLikeRepository extends JpaRepository<SongLike, String> {
}
