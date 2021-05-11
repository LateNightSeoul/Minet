package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.UploadSongResponse;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.service.FileService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    SongRepository songRepository;

    @PostMapping("/upload/song")
    public ResponseEntity<UploadSongResponse> uploadSong(@RequestParam("file") MultipartFile uploadFile, @RequestParam("me") String info, Principal principal) {

        try {
            JSONObject jsonInfo = new JSONObject(info);
            UploadSongDto uploadSongDto = fileService.getSongDto(jsonInfo);

            String fileName = fileService.saveSong(uploadFile, principal, uploadSongDto);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
                    .path(fileName).toUriString();

            fileName.substring(fileName.lastIndexOf(".") + 1);

            Song uploadedSong = fileService.saveSongInfo(uploadFile, principal, fileName, fileDownloadUri);

            return new ResponseEntity<UploadSongResponse>(new UploadSongResponse(fileName, uploadFile.getContentType(), uploadFile.getSize(), uploadedSong),
                    HttpStatus.OK);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileStorageException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/upload/song/info")
//    public
}
