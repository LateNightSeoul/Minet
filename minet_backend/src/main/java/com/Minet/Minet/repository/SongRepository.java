package com.Minet.Minet.repository;

import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long>, SongCustomRepository {
    Song findByAlbumChildId(AlbumChildId albumChildId);
}
