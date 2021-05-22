package com.Minet.Minet.repository;

import com.Minet.Minet.domain.statistic.TotalVisited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalVisitedRepository extends JpaRepository<TotalVisited, String> {
}
