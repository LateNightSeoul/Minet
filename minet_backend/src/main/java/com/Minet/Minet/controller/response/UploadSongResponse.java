package com.Minet.Minet.controller.response;


import com.Minet.Minet.domain.music.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadSongResponse {

    private String message;

    private String albumName;
}
