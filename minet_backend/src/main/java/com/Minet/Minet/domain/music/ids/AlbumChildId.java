package com.Minet.Minet.domain.music.ids;

import com.Minet.Minet.domain.music.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumChildId implements Serializable {

    private ArtistChildId artistChildId;

    @Column(name = "song_url")
    private String songUrl;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
