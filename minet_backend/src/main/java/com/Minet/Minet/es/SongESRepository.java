package com.Minet.Minet.es;

import org.elasticsearch.action.search.SearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongESRepository extends ElasticsearchRepository<Song, String> {
}
