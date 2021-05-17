package com.Minet.Minet.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class elasticRepositoryTest {

    @Autowired
    SongESRepository songESRepository;

    @Autowired
    RestHighLevelClient client;

    @Test
    public void testMember() throws Exception {
        Song song = new Song();
        song.setId("1");
        song.setArtist("sik-k");
        songESRepository.save(song);
    }

    @Test
    public void testSearchAndPaging() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        for(int i = 0; i < 5; i++) {
            searchSourceBuilder.from(i);
            searchSourceBuilder.size(1);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println(searchResponse);
        }
    }

    @Test
    public void MultiMatchQuery() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("late", "artist.ngram", "songName.ngram"));
        for(int i = 0; i < 5; i++) {
            searchSourceBuilder.from(i);
            searchSourceBuilder.size(1);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println(searchResponse);

            SearchHits searchHits = searchResponse.getHits();

            for (SearchHit hit : searchHits) {
                System.out.println("hit : " + hit.toString());
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                System.out.println(sourceAsMap);
            }
        }
    }
}
