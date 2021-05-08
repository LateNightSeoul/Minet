package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.JoinArtistDto;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Artist joinArtist(JoinArtistDto joinArtistDto) {
        if(memberRepository.findOneByUserid(joinArtistDto.getUserid()) != null) {
            throw new RuntimeException("이미 가입된 id입니다.");
        }

        Member member = Member.builder()
                .username(joinArtistDto.getUsername())
                .userid(joinArtistDto.getUserid())
                .password(passwordEncoder.encode(joinArtistDto.getPassword()))
                .phone(joinArtistDto.getPhone())
                .enabled(true)
                .createTime(LocalDateTime.now())
                .role(Authority.ROLE_ARTIST)
                .build();

        memberRepository.save(member);

        Artist artist = Artist.builder()
                .member(member)
                .artistName(joinArtistDto.getArtistName())
                .genre(joinArtistDto.getGenre())
                .birth(joinArtistDto.getBirth())
                .profileUrl(joinArtistDto.getProfileUrl())
                .build();

        return artistRepository.save(artist);
    }
}
