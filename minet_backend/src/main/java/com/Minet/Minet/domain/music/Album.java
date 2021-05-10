package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ArtistChildId.class)
public class Album {

    @Id @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    private String albumName;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String photoUrl;

    private String albumUrl;



}
