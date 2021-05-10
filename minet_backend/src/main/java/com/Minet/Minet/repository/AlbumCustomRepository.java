package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class AlbumCustomRepository {

    @Autowired
    EntityManager em;

    Album save(Album album) {
        return (Album) em.createQuery("INSERT INTO Album")
                .getSingleResult();
    }
}
