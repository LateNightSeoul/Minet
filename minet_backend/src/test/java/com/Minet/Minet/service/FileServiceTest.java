package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.repository.AlbumRepository;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Test
    public void 파일이름변경테스트(){
        File file = new File("nav.mp3");
        System.out.println(file.getAbsoluteFile());
        String artistName = "nav";

        String fileName = StringUtils.cleanPath(artistName + "/" + file.getAbsolutePath());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        System.out.println(fileName + " " + fileExtension);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 엔티티저장_식별관계() {
        Member member = new Member();

        Artist artist = new Artist();

        artist.setArtistName("ㅎㅇ용");
        member.setEmail("leehae8@NAVER.COM");
        member.setArtist(artist);

        memberRepository.save(member);

        Album album = new Album();
        ArtistChildId artistChildId = new ArtistChildId(artist.getId(), "11335");
        album.setAlbumName("제발 그만");
        album.setArtistChildId(artistChildId);
        album.setArtist(artist);

        albumRepository.save(album);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void 회원저장() {
        Member member = new Member();
        member.setEmail("gkdsfjsdfl");
        memberRepository.save(member);
    }


}