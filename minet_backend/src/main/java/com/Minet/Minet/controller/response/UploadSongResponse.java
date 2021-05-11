package com.Minet.Minet.controller.response;


import com.Minet.Minet.domain.music.Song;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UploadSongResponse {

    private String message;

    private String fileName;

    private String fileType;

    private long size;

    private Song song;
}
