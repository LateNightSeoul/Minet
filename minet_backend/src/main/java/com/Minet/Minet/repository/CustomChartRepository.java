package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomChartRepository {

    @Autowired
    EntityManager em;

//    public List<Song> createDailyChart() {
//
//    }
}
