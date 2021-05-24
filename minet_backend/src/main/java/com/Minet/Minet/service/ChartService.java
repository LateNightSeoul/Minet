package com.Minet.Minet.service;

import com.Minet.Minet.controller.response.RisingChartResponse;
import com.Minet.Minet.domain.enumTypes.ChartType;
import com.Minet.Minet.domain.music.Chart;
import com.Minet.Minet.domain.music.ChartSong;
import com.Minet.Minet.repository.ChartRepository;
import com.Minet.Minet.repository.ChartSongRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;

    @Autowired
    ChartSongRepository chartSongRepository;

    @Transactional
    public List<RisingChartResponse> getRisingChart() throws IllegalAccessException {
        Chart risingChart = null;
        List<Chart> findChart = chartRepository.findByChartDate(LocalDate.now());
        for(Chart chart : findChart) {
            if(chart.getChartType().equals(ChartType.Rising)) {
                risingChart = chart;
            }
        }
        if(risingChart == null) {
            throw new IllegalAccessException("차트가 존재하지 않습니다.");
        }
        List<RisingChartResponse> risingChartResponses = new ArrayList<>();
        List<ChartSong> chartSongs = chartSongRepository.findChartSongsByChart(risingChart);

        for (ChartSong chartSong : chartSongs) {
            
        }
    }
}
