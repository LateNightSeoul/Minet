package com.Minet.Minet.repository;

import com.Minet.Minet.domain.member.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
