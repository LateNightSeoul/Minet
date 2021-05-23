package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.SongChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class TotalVisited {

    @EmbeddedId
    private SongChildId songChildId;

    @MapsId("albumChildId")
    @OneToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id"),
            @JoinColumn(name = "album_url"),
            @JoinColumn(name = "song_url"),

    })
    private Song song;

    private Long count;

    private LocalDateTime createDate;
}
