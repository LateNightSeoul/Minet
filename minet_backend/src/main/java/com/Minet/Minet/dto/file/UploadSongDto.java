package com.Minet.Minet.dto.file;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.music.Album;
import lombok.Data;

@Data
public class UploadSongDto {

    private Album album;

    private Artist artist;

    private Genre genre;

    private String songName;
}
