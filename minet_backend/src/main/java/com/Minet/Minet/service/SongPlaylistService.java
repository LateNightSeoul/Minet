package com.Minet.Minet.service;

import com.Minet.Minet.repository.SongPlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongPlaylistService {

    private final SongPlaylistRepository songPlaylistRepository;

}
