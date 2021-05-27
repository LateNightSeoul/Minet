package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.domain.music.ids.SongLikeId;
import com.Minet.Minet.domain.statistic.SongLike;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongLikeRepository;
import com.Minet.Minet.repository.SongRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SongLikeService {

    @Autowired
    SongLikeRepository songLikeRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public SongLike addSongLike(Principal principal, String songUrl, String albumUrl, Long artistId) throws IllegalAccessException {
        Optional<Member> member = memberRepository.findOneByUserid(principal.getName());
        if(member.isEmpty()) {
            throw new IllegalAccessException("해당하는 member 정보가 없습니다.");
        }
        SongLike songLike = new SongLike();
        songLike.setMember(member.get());
        ArtistChildId artistChildId = new ArtistChildId(artistId, albumUrl);
        AlbumChildId albumChildId = new AlbumChildId(artistChildId, songUrl);
        SongLikeId songLikeId = new SongLikeId(albumChildId, LocalDateTime.now());
        Song findSong = songRepository.findByAlbumChildId(albumChildId);
        songLike.setSongLikeId(songLikeId);
        songLike.setSong(findSong);
        songLike.setMember(member.get());
        return songLikeRepository.save(songLike);
    }

}
