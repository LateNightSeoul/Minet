package com.Minet.Minet.dto.file;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UploadSongInfoDto {

    private int songNumber;

    private String fileName;

    private String songName;

    private String albumName;

    private String artist;

    private Genre genre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
}
