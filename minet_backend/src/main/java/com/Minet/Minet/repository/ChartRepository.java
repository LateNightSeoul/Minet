package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    List<Chart> findByChartDate(LocalDate date);
}
