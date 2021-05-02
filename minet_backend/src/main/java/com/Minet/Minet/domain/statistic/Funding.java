package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Funding {

    @Id @GeneratedValue
    @Column(name = "funding_id")
    private Long id;

    private BigDecimal amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private LocalDateTime createTime;
}
