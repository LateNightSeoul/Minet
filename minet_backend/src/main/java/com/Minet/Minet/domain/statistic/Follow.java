package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Follow {

    @Id @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;
}
