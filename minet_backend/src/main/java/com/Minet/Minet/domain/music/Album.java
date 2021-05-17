package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @EmbeddedId
    private ArtistChildId artistChildId;

    @MapsId("artist_id")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    private LocalDate releaseDate;

    private String albumName;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Song> songs = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String photoUrl;
}
