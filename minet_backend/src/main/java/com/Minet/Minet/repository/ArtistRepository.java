package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findOneByArtistName(String artistName);
    Optional<Artist> findById(Long artistId);
}
