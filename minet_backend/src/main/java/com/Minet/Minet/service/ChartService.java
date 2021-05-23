package com.Minet.Minet.service;

import com.Minet.Minet.domain.music.Chart;
import com.Minet.Minet.domain.music.ChartSong;
import com.Minet.Minet.repository.ChartRepository;
import com.Minet.Minet.repository.ChartSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    ChartSongRepository chartSongRepository;

    @Transactional
    public List<ChartSong> getRisingChart() {
        Chart findChart = chartRepository.findByChartDate(LocalDate.now());
        return chartSongRepository.findChartSongsByChart(findChart);
    }
}
