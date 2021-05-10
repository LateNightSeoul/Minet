package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.AlbumRepository;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    EntityManager em;

    private final Path fileStorageLocation;

    public FileService(FilePath filePath) {
        this.fileStorageLocation = Paths.get(filePath.getPath()).toAbsolutePath().normalize();
    }

    public String saveSong(MultipartFile file, Principal principal){
        Artist artist = memberRepository.findOneByUserid(principal.getName()).get().getArtist();
        String fileName = StringUtils.cleanPath(artist.getArtistName() + "/" + file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("파일 이름에는 ..이 들어갈 수 없습니다.");
            }

            Path targetFolder = this.fileStorageLocation.resolve(artist.getArtistName());

            if(!Files.exists(targetFolder)) {
                Files.createDirectory(targetFolder);
            }

            Path targetPath = this.fileStorageLocation.resolve(fileName);

            if(Files.exists(targetPath)) {
                throw new FileStorageException("중복되는 파일명 입니다.");
            }

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (FileStorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public boolean isArtist(String userId) {
        Optional<Member> member = memberRepository.findOneByUserid(userId);
        if(member.get().getAuthority() == Authority.ROLE_ARTIST) {
            return true;
        }
        return false;
    }
    @Transactional
    public Song saveSongInfo(MultipartFile uploadFile, Principal principal,
                             String fileName, String fileDownloadUri) {

        Member member = memberRepository.findOneByUserid(principal.getName()).get();
        Artist findArtist = member.getArtist();
        Album album = new Album();
        ArtistChildId artistChildId = new ArtistChildId(findArtist.getId(), Long.valueOf(1));
        album.setArtist(findArtist);
        album.setArtistChildId(artistChildId);
        Album saveAlbum = albumRepository.save(album);

        System.out.println("***********************************************");

        Song song = Song.builder()
                .album(album)
                .createTime(LocalDateTime.now())
                .fileType(uploadFile.getContentType())
                .downloadUri(fileDownloadUri)
                .songUrl(fileName)
                .photoUrl(null)
                .size(uploadFile.getSize())
                .build();

        System.out.println(" **********************" + song.getAlbum().getArtist().getId() + "++++++++" );

        return songRepository.save(song);
    }
}
