package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.SongChildId;
import com.Minet.Minet.domain.music.ids.SongLikeId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class DailyVisited {

    @EmbeddedId
    private SongLikeId songLikeId;

    @MapsId("albumChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "album_url"),
            @JoinColumn(name = "artist_id"),
            @JoinColumn(name = "song_url")
    })
    private Song song;

    private Long count;

}
