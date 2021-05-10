package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.repository.AlbumRepository;
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
        Member member = memberRepository.findOneByUserid("leehae10").get();
        Artist findArtist = member.getArtist();
        Album album = new Album();
        ArtistChildId artistChildId = new ArtistChildId(findArtist.getId(), Long.valueOf(1));
        album.setArtist(findArtist);
        album.setAlbumName("제발 그만  ");
        album.setArtistChildId(artistChildId);
        Album saveAlbum = albumRepository.save(album);

        Album album2 = new Album();
        ArtistChildId artistChildId2 = new ArtistChildId(findArtist.getId(), Long.valueOf(111));
        album2.setArtist(findArtist);
        album2.setAlbumName("안녕 ");
        album2.setArtistChildId(artistChildId2);
        Album saveAlbum2 = albumRepository.save(album2);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 회원저장() {
        Member member = new Member();
        member.setUserid("gkdsfjsdfl");
        memberRepository.save(member);
    }


}