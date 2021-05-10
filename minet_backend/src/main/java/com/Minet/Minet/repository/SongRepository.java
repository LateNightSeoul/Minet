package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}
