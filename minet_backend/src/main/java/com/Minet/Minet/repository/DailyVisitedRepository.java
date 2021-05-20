package com.Minet.Minet.repository;

import com.Minet.Minet.domain.statistic.DailyVisited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyVisitedRepository extends JpaRepository<DailyVisited, String> {

}
