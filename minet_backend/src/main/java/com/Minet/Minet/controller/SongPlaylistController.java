package com.Minet.Minet.controller;

import com.Minet.Minet.service.SongPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/like")
public class SongPlaylistController {

    private final SongPlaylistService songPlaylistService;

    

}
