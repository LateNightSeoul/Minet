package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @EmbeddedId
    private AlbumChildId albumChildId;

    @MapsId("artistChildId")
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id", referencedColumnName = "artist_id"),
            @JoinColumn(name = "album_url", referencedColumnName = "album_url")
    })
    private Album album;

    private Genre genre;

    private String songName;

    private String downloadUri;

    private String fileType;

    private Long size;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
