package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.SongChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Visited {
    @EmbeddedId
    private SongChildId songChildId;

    @MapsId("albumChildId")
    @OneToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id"),
            @JoinColumn(name = "album_id"),
            @JoinColumn(name = "song_id")
    })
    private Song song;

    private Long count;

}
