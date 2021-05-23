package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    Chart findByChartDate(LocalDate date);
}
