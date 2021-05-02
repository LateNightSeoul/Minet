package com.Minet.Minet.domain.content;

import com.Minet.Minet.domain.member.Artist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class PageContent {

    @Id @GeneratedValue
    @Column(name = "content_id")
    private Long Id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "artist_page_id")
    private ArtistPage artistPage;

    @Lob
    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "pageContent", cascade = CascadeType.ALL)
    private List<ContentPhoto> contentPhotos = new ArrayList<>();
}
