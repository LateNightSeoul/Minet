package com.Minet.Minet.domain.member;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Artist {

    @Id @GeneratedValue
    @Column(name = "artist_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String artist_name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate birth;

}
