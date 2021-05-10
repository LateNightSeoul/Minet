package com.Minet.Minet.domain.statistic;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.SongChildId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class SongLike {

    @EmbeddedId
    private SongChildId songChildId;

    @MapsId("albumChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id"),
            @JoinColumn(name = "album_id"),
            @JoinColumn(name = "song_id")
    })

    private Song song;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
