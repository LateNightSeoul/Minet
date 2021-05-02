package com.Minet.Minet.domain.content;

import com.Minet.Minet.domain.member.Artist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class ArtistPage {

    @Id @GeneratedValue
    @Column(name = "artist_page_id")
    private Long Id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private String biography;
}
