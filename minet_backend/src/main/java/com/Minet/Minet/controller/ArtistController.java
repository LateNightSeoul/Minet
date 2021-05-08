package com.Minet.Minet.controller;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.dto.member.JoinArtistDto;
import com.Minet.Minet.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping("/join/artist")
    public ResponseEntity<Artist> join(
            @RequestBody JoinArtistDto joinArtistDto
            ) {
        return ResponseEntity.ok(artistService.joinArtist(joinArtistDto));
    }
}
