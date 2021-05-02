package com.Minet.Minet.domain.music.ids;

import com.Minet.Minet.domain.music.Album;

import java.io.Serializable;

public class AlbumChildId implements Serializable {
    private Album album;
    private Long id;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
