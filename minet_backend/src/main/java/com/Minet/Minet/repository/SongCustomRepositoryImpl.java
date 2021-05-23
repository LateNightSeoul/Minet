package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Song;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class SongCustomRepositoryImpl implements SongCustomRepository<Song>{

    @Autowired
    EntityManager em;

    @Override
    public Song findBySongUrl(String songUrl) {
        return new Song();
    }
}
