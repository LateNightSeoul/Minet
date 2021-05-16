package com.Minet.Minet.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Data
@Document(indexName = "song")
public class Song {

    @Id
    private String id;
    private String title;
    private String artist;
    private String lyric;
}
