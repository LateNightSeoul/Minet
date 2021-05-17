package com.Minet.Minet.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongESRepository extends ElasticsearchRepository<Song, String>, SongESCustomRepository<Song> {

}
