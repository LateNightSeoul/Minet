package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ArtistRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveArtist(Artist artist) {
        em.persist(artist);
        return artist.getId();
    }

    public Artist findArtist(Long id) {
        return em.find(Artist.class, id);
    }
}
