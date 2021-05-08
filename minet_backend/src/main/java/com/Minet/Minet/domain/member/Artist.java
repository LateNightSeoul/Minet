package com.Minet.Minet.domain.member;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Member member;

    private String artistName;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate birth;

    private String profileUrl;

}
