package com.Minet.Minet.es;

import com.Minet.Minet.dto.file.UploadSongInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class SongESService {

    @Autowired
    SongESRepository songESRepository;

//    public Slice<Song> findAll(String search, PageRequest pageRequest) {
//        return songESRepository.findAllByArtist(pageRequest);
//    }

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
}
