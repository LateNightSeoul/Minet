package com.Minet.Minet.domain.member;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.music.Album;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id @GeneratedValue
    @Column(name = "artist_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    private String artistName;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate birth;

    private String profileUrl;

}
