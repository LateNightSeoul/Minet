package com.Minet.Minet.service;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.dto.file.UploadSongInfoDto;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.AlbumRepository;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public String saveFile(MultipartFile file, String dirPath) throws FileStorageException, IOException{

        Path targetPath = this.fileStorageLocation.resolve(StringUtils.cleanPath(dirPath + file.getOriginalFilename()));

        if(Files.exists(targetPath)) {
            throw new FileStorageException("중복된 파일 이름 입니다.");
        }

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return targetPath.toString();
    }

    public String makedir(List<String> folderNames) throws IOException {
        StringBuilder dirPath = new StringBuilder("");
        for(String folderName : folderNames) {
            dirPath.append(StringUtils.cleanPath(folderName + "/"));
            Path targetFolder = this.fileStorageLocation.resolve(String.valueOf(dirPath));

            if(!Files.exists(targetFolder)) {
                Files.createDirectory(targetFolder);
            }
        }
        return dirPath.toString();
    }

    @Transactional
    public Album saveAlbumInfo(Member currentUser, UploadSongInfoDto songInfo, String imagePath) {
        Artist userArtist = currentUser.getArtist();
        String albumUrl = imagePath.substring(0, StringUtils.cleanPath(imagePath).lastIndexOf("/") + 1);

        ArtistChildId artistChildId = new ArtistChildId(userArtist.getId(), albumUrl);

         Album album = Album.builder()
                .artist(userArtist)
                .artistChildId(artistChildId)
                .albumName(songInfo.getAlbumName())
                .genre(songInfo.getGenre())
                .releaseDate(songInfo.getReleaseDate())
                .photoUrl(imagePath)
                .build();
         return albumRepository.save(album);
    }

    @Transactional
    public Song saveSongInfo(Album albumSaved, Member currentUser, UploadSongInfoDto songInfo, String filePath, MultipartFile uploadFile) {

        AlbumChildId albumChildId = new AlbumChildId(albumSaved.getArtistChildId(), filePath);

        Song song = Song.builder()
                .songName(songInfo.getSongName())
                .fileType(uploadFile.getContentType())
                .createTime(LocalDateTime.now())
                .genre(songInfo.getGenre())
                .albumChildId(albumChildId)
                .size(uploadFile.getSize())
                .album(albumSaved)
                .songNumber(songInfo.getSongNumber())
                .build();

        return songRepository.save(song);
    }
}
