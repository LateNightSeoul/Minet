package com.Minet.Minet.es;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Document(indexName = "song")
public class Song {

    @Id
    private String id;

    private String songName;

    private String albumName;

    private String artist;

    private String albumUrl;

    private String songUrl;

    private String imageUrl;

    private LocalDate releaseDate;

    private Genre genre;
}
