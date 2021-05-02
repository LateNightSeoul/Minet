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
@IdClass(SongChildId.class)
public class WeeklyVisited {
    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name="song_id"),
            @JoinColumn(name = "album_id"),
            @JoinColumn(name = "artist_id")
    })
    private Song song;

    private Long count;

    private LocalDateTime date;

}
