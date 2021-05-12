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
import java.util.ArrayList;
import java.util.List;

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
    public UploadSongResponse uploadSong(@RequestParam("file") MultipartFile uploadSong,
                                                         @RequestParam("info") String info,
                                                         @RequestParam("image") MultipartFile uploadImage,
                                                         Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.equals(Authority.ROLE_ARTIST.toString()))){
            throw new AccessDeniedException("Artist 권한이 없습니다.");
        }

        if(uploadSong.isEmpty()) {
            throw new IllegalAccessException("파일이 존재하지 않습니다.");
        }

        JSONObject jsonInfo = new JSONObject(info);
        UploadSongDto uploadSongDto = fileService.getSongDto(jsonInfo);

        String songPath = fileService.saveFile(uploadSong, uploadSongDto);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
                .path(songPath).toUriString();

        StringBuilder imagePath = new StringBuilder("");

        if(!uploadImage.isEmpty()) {
            imagePath.append(fileService.saveFile(uploadImage, uploadSongDto));
        }

        Song uploadedSong = fileService.saveSongInfo(uploadSong, principal, songPath, String.valueOf(imagePath), fileDownloadUri, uploadSongDto);

        return new UploadSongResponse("저장 성공", songPath, uploadSong.getContentType(), uploadSong.getSize(), uploadedSong);

    }

    public List<UploadSongResponse> uploadAlbum(@RequestParam("files") MultipartFile[] files,
                                                @RequestParam("infos") String[] infos,
                                                @RequestParam("image") MultipartFile[] image,
                                                Principal principal) {
        List<UploadSongResponse> responses = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            responses.add((uploadSong(files[i], infos[i], image[i], principal)));
        }
        return responses;
    }
}
