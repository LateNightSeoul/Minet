package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.JoinDto;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.security.Authority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(JoinDto joinDto) {
        if(memberRepository.findOneByUserid(joinDto.getUserid()) != null) {
            throw new RuntimeException("이미 가입된 id입니다.");
        }

        Member member = Member.builder()
                .username(joinDto.getUsername())
                .userid(joinDto.getUserid())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .phone(joinDto.getPhone())
                .enabled(true)
                .createTime(LocalDateTime.now())
                .authority(joinDto.getAuthority())
                .build();

        Member savedMember = memberRepository.save(member);

        if(joinDto.getAuthority().equals(Authority.ROLE_ARTIST)) {
            Artist artist = Artist.builder()
                    .member(member)
                    .artistName(joinDto.getArtistName())
                    .genre(joinDto.getGenre())
                    .birth(joinDto.getBirth())
                    .profileUrl(joinDto.getProfileUrl())
                    .build();
            Artist savedArtist = artistRepository.save(artist);
            savedMember.setArtist(savedArtist);
        }
        return savedMember;
    }
}
