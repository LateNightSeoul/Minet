package com.Minet.Minet.controller;

import com.Minet.Minet.service.SongLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/like")
public class LikeController {

    @Autowired
    SongLikeService songLikeService;


    @PostMapping("/addSongLike")
    public void addSongLike(Principal principal, @RequestParam("song_url") String songUrl,
                            @RequestParam("album_url") String albumUrl, @RequestParam("artist_id") Long artistId) throws IllegalAccessException {
        songLikeService.addSongLike(principal, songUrl, albumUrl, artistId);
    }
}
