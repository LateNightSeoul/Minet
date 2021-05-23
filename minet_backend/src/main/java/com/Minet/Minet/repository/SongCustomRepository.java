package com.Minet.Minet.repository;

public interface SongCustomRepository<Song> {
    Song findBySongUrl(String songUrl);
}
