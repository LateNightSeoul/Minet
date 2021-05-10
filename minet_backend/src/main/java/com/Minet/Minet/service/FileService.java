package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.security.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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
    SongRepository songRepository;

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

            Path targetPath = this.fileStorageLocation.resolve(fileName);
            if(Files.exists(targetPath)) {
                throw new FileStorageException("중복되는 파일명 입니다.");
            }

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.ATOMIC_MOVE);

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

    public Song saveSongInfo(MultipartFile uploadFile, Principal principal,
                             String fileName, String fileDownloadUri) {

        Album album = new Album();
        album.setArtist(memberRepository.findOneByUserid(principal.getName()).get().getArtist());

        Song song = Song.builder()
                .album(album)
                .createTime(LocalDateTime.now())
                .fileType(uploadFile.getContentType())
                .downloadUri(fileDownloadUri)
                .songUrl(fileName)
                .photoUrl(null)
                .size(uploadFile.getSize())
                .build();

        return songRepository.save(song);
    }
}
