package com.Minet.Minet.converter;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.dto.file.UploadSongInfoDto;
import com.Minet.Minet.dto.file.UploadSongInfoWrapperDto;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SongInfoConverter {

    public UploadSongInfoDto buildUploadSongInfoDto(String songInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject(songInfo);
        UploadSongInfoDto uploadSongInfoDto = UploadSongInfoDto.builder().songName(jsonObject.getString("songName"))
                .albumName(jsonObject.getString("albumName"))
                .songNumber(jsonObject.getInt("songNumber"))
                .releaseDate(LocalDate.parse(jsonObject.getString("releaseDate"), DateTimeFormatter.ISO_DATE))
                .artist(jsonObject.getString("artist"))
                .genre(Genre.fromString(jsonObject.getString("genre")))
                .fileName(jsonObject.getString("fileName")).build();
        return uploadSongInfoDto;
    }

    public UploadSongInfoWrapperDto getSongInfo(String[] songInfoStrings) throws JSONException {
        UploadSongInfoWrapperDto songInfos = new UploadSongInfoWrapperDto();
        for (String songInfo : songInfoStrings) {
            songInfos.getUploadSongInfoDto().add(buildUploadSongInfoDto(songInfo));
        }
        return songInfos;
    }
}
