package com.Minet.Minet.repository;

import com.Minet.Minet.domain.statistic.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, String> {
}
