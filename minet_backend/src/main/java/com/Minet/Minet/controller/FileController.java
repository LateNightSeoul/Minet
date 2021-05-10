package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.UploadSongResponse;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public UploadSongResponse uploadSong(@RequestPart("file") MultipartFile uploadFile, Principal principal) {

        String fileName = fileService.saveSong(uploadFile, principal);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
                .path(fileName).toUriString();

        Song uploadedSong = fileService.saveSongInfo(uploadFile, principal, fileName, fileDownloadUri);

        return new UploadSongResponse(fileName, uploadFile.getContentType(), uploadFile.getSize(), uploadedSong);
    }
}
