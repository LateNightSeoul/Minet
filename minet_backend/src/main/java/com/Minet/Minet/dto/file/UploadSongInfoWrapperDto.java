package com.Minet.Minet.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadSongInfoWrapperDto {
    private List<UploadSongInfoDto> uploadSongInfoDto = new ArrayList<>();
}
