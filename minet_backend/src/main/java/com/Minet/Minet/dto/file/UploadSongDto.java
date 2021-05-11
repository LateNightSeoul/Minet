package com.Minet.Minet.dto.file;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.music.Album;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UploadSongDto {

    private String songName;

    private String albumName;

    private String artist;

    private String genre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
}
