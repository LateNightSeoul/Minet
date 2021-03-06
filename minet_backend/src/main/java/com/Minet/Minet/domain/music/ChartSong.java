package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.music.ids.SongChildId;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumns({
            @JoinColumn(name="song_url", referencedColumnName = "song_url"),
            @JoinColumn(name = "album_url", referencedColumnName = "album_url"),
            @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    })
    private Song song;

    private Long rank;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chart_id")
    private Chart chart;

    private String songName;

    private String artistName;

    @Enumerated(EnumType.STRING)
    private Genre genre;

}
