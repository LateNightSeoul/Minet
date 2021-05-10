package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.SongChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class AlbumLike {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @EmbeddedId
    private AlbumChildId albumChildId;

    @MapsId("artistChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "album_id"),
            @JoinColumn(name = "artist_id")
    })
    private Album album;

}
