package com.Minet.Minet.es;

import com.Minet.Minet.dto.file.UploadSongInfoDto;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SongESService {

    @Autowired
    SongESRepository songESRepository;


    public void save(String dirPath, String songPath, String imagePath, UploadSongInfoDto uploadSongInfoDto) {
        Song song = new Song();
        song.setId(songPath);
        song.setArtist(uploadSongInfoDto.getArtist());
        song.setSongName(uploadSongInfoDto.getSongName());
        song.setSongUrl(songPath);
        song.setAlbumUrl(dirPath);
        song.setImageUrl(imagePath);
        song.setGenre(uploadSongInfoDto.getGenre());
        song.setReleaseDate(uploadSongInfoDto.getReleaseDate());

        songESRepository.save(song);
    }

    public List<Map<String, Object>> search(String keyword, String type, Pageable pageable) throws IOException {
        SearchHits searchHits = songESRepository.search(keyword, type, pageable);
        List<Map<String, Object>> searchResult = new ArrayList<>();
        for(SearchHit hit : searchHits) {
            searchResult.add(hit.getSourceAsMap());
        }
        return searchResult;
    }
}
