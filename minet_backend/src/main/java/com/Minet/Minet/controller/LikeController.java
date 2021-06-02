package com.Minet.Minet.controller;

import com.Minet.Minet.domain.statistic.SongLike;
import com.Minet.Minet.dto.social.LikeAddDto;
import com.Minet.Minet.service.SongLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final SongLikeService songLikeService;

    @PostMapping("/addSongLike")
    public ResponseEntity addSongLike(Principal principal, @RequestBody LikeAddDto likeAddDto) throws IllegalAccessException {
        SongLike saveSongLike = songLikeService.addSongLike(principal, likeAddDto.getSongUrl(), likeAddDto.getAlbumUrl(), likeAddDto.getArtistId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getSongLike")
    public List<SongLike> getSongLike(Principal principal) throws IllegalAccessException {
        return songLikeService.getSongLike(principal);
    }
}
