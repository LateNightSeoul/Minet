package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@IdClass(AlbumChildId.class)
public class AlbumLike {

    @Id @GeneratedValue
    @Column(name = "album_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "album_id"),
            @JoinColumn(name = "artist_id")
    })
    private Album album;

}
