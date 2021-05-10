package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.music.ids.SongChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class ChartSong {

    @EmbeddedId
    private SongChildId songChildId;

    @MapsId("albumChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name="song_id", referencedColumnName = "song_id"),
            @JoinColumn(name = "album_id", referencedColumnName = "album_id"),
            @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    })
    private Song song;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chart_id")
    private Chart chart;

}
