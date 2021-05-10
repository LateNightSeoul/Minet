package com.Minet.Minet.controller.response;


import com.Minet.Minet.domain.music.Song;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UploadSongResponse {

    private String fileName;

    private String fileType;

    private long size;

    private Song song;
}
