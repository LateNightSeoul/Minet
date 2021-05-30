package com.Minet.Minet.controller;

import com.Minet.Minet.domain.statistic.SongLike;
import com.Minet.Minet.dto.social.LikeAddDto;
import com.Minet.Minet.service.SongLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final SongLikeService songLikeService;

    @PostMapping("/addSongLike")
    public String addSongLike(Principal principal, @RequestBody LikeAddDto likeAddDto) throws IllegalAccessException {
        SongLike saveSongLike = songLikeService.addSongLike(principal, likeAddDto.getSongUrl(), likeAddDto.getAlbumUrl(), likeAddDto.getArtistId());
        return "hi요";
    }
}
