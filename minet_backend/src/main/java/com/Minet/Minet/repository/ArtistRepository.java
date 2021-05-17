package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findOneByArtistName(String artistName);
}
