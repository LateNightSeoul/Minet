package com.Minet.Minet.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class SongESCustomRepositoryImpl implements SongESCustomRepository {

    @Autowired
    RestHighLevelClient client;

    @Override
    public SearchHits search(String keyword, String type, Pageable pageable) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "artist.ngram", "songName.ngram"));
        searchSourceBuilder.from((int)(pageable.getPageNumber() * pageable.getPageSize()));
        searchSourceBuilder.size(pageable.getPageSize());
        if (type.equals("recent")) {
            searchSourceBuilder.sort(new FieldSortBuilder("releaseDate").order(SortOrder.DESC));
        }
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getHits();
    }
}
