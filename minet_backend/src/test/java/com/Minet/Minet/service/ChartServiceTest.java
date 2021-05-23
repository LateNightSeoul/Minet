package com.Minet.Minet.service;

import com.Minet.Minet.domain.music.ChartSong;
import com.Minet.Minet.repository.ChartRepository;
import com.Minet.Minet.repository.ChartSongRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartServiceTest {

    @Autowired
    ChartService chartService;

    @Test
    @Transactional
    @Rollback(false)
    public void findChart() throws Exception {
        List<ChartSong> risingChart = chartService.getRisingChart();
        for (ChartSong song : risingChart) {
            System.out.println(song.getSongName() + " " + song.getSongChildId() + " " + song.getGenre());
        }
    }
}
