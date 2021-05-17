package com.Minet.Minet.dto.file;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UploadAlbumDto {
    private MultipartFile[] song;
    private UploadSongInfoDto[] songInfo;
}
