package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@IdClass(AlbumChildId.class)
public class Song {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id", referencedColumnName = "artist_id"),
            @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    })
    private Album album;

    private Genre genre;

    private String songName;




}
