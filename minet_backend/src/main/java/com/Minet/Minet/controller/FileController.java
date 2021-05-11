package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.UploadSongResponse;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.security.Authority;
import com.Minet.Minet.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    MemberRepository memberRepository;

    @SneakyThrows
    @PostMapping("/upload/song")
    public ResponseEntity<UploadSongResponse> uploadSong(@RequestParam("file") MultipartFile uploadSong,
                                                         @RequestParam("me") String info,
                                                         @RequestParam("image") MultipartFile uploadImage,
                                                         Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.equals(Authority.ROLE_ARTIST.toString()))){
            throw new AccessDeniedException("Artist 권한이 없습니다.");
        }

        JSONObject jsonInfo = new JSONObject(info);
        UploadSongDto uploadSongDto = fileService.getSongDto(jsonInfo);

        String songPath = fileService.saveFile(uploadSong, principal, uploadSongDto);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
                .path(songPath).toUriString();

        String imagePath = fileService.saveFile(uploadImage, principal, uploadSongDto);

        Song uploadedSong = fileService.saveSongInfo(uploadSong, principal, songPath, imagePath, fileDownloadUri, uploadSongDto);

        return new ResponseEntity<>(new UploadSongResponse("저장 성공", songPath, uploadSong.getContentType(), uploadSong.getSize(), uploadedSong),
                HttpStatus.OK);
    }
}
