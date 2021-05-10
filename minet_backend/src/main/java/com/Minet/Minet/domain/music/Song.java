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
@IdClass(AlbumChildId.class)
public class Song {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "artist_id", referencedColumnName = "artist_id"),
            @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    })
    private Album album;

    private Genre genre;

    private String songName;

    private String photoUrl;

    private String songUrl;

    private String downloadUri;

    private String fileType;

    private Long size;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
