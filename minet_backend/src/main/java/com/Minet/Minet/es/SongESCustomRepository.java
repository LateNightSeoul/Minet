package com.Minet.Minet.es;

import org.elasticsearch.search.SearchHits;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface SongESCustomRepository<Song> {

    SearchHits search(String keyword, String type, Pageable pageable) throws IOException;

}
