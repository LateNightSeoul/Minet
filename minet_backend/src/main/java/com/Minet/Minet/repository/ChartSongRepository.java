package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Chart;
import com.Minet.Minet.domain.music.ChartSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartSongRepository extends JpaRepository<ChartSong, String> {
    List<ChartSong> findChartSongsByChart(Chart chart);
}
