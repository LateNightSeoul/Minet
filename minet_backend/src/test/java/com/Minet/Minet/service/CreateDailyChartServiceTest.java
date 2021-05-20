package com.Minet.Minet.service;

import com.Minet.Minet.domain.music.Song;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateDailyChartServiceTest {


    @Test
    @Transactional
    @Rollback(false)
    public void createChart() throws Exception {

        Song song1 = Song.builder()
                .


    }
}
