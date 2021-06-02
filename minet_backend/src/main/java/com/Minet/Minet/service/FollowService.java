package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.statistic.Follow;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.FollowRepository;
import com.Minet.Minet.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final ArtistRepository artistRepository;

    public Follow addFollow(Principal principal, Long artistId) {
        Optional<Member> findMember = memberService.findByUserId(principal.getName());
        if (findMember.isEmpty()) {
            throw new AccessDeniedException("no such Member");
        }
        Optional<Artist> findArtist = artistRepository.findById(artistId);
        if (findArtist.isEmpty()) {
            throw  new AccessDeniedException("no such Artist");
        }

        Follow follow = new Follow();
        follow.setMember(findMember.get());
        follow.setArtist(findArtist.get());

        return followRepository.save(follow);
    }

    public List<Follow> getFollowList(Principal principal) {
        Optional<Member> findMember = memberService.findByUserId(principal.getName());
        if (findMember.isEmpty()) {
            throw new AccessDeniedException("no such Member");
        }
        return followRepository.findAllByMember(findMember.get());
    }
}
