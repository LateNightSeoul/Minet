package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Song;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CustomSongRepository {

    @Autowired
    EntityManager em;

//    public Song findSongUrl() {
//        Song findSong = em.createQuery("select s from Song s, Album a where a.albumName = ")
//    }
}
