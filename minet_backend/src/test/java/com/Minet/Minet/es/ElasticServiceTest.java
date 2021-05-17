package com.Minet.Minet.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticServiceTest {

    @Autowired
    SongESRepository songESRepository;

    @Autowired
    SongESService songESService;

    @Test
    public void testSearchRecent() throws Exception {
        Song song = new Song();
        song.setId("imhaesuk/3");
        song.setSongName("nigagara hawai");
        song.setArtist("LateNight");
        song.setReleaseDate(LocalDate.of(2009, 3, 2));
        songESRepository.save(song);

        Song song2 = new Song();
        song2.setId("imhaesuk/4");
        song2.setSongName("nigagara hawai");
        song2.setArtist("LateNight");
        song2.setReleaseDate(LocalDate.of(2010, 3, 2));
        songESRepository.save(song2);

        Song song3 = new Song();
        song3.setId("imhaesuk/5");
        song3.setSongName("nigagara hawai");
        song3.setArtist("LateNight");
        song3.setReleaseDate(LocalDate.of(2007, 1, 1));
        songESRepository.save(song3);


    }
}
