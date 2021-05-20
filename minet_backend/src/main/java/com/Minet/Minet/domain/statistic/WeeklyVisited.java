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
public class WeeklyVisited {

    @EmbeddedId
    private SongChildId songChildId;

    @MapsId("albumChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name="song_url"),
            @JoinColumn(name = "album_url"),
            @JoinColumn(name = "artist_id")
    })
    private Song song;

    private Long count;

    private LocalDateTime createDate;

}
