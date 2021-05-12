package com.Minet.Minet.service;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.dto.file.UploadSongDto;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.AlbumRepository;
import com.Minet.Minet.repository.ArtistRepository;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    public UploadSongDto getSongDto(JSONObject jsonObject) throws JSONException {

        UploadSongDto uploadSongDto = new UploadSongDto();
        uploadSongDto.setSongName(jsonObject.getString("songName"));
        uploadSongDto.setAlbumName(jsonObject.getString("albumName"));
        uploadSongDto.setArtist(jsonObject.getString("artist"));
        uploadSongDto.setGenre(Genre.fromString(jsonObject.getString("genre")));
        uploadSongDto.setReleaseDate(LocalDate.parse(jsonObject.getString("releaseDate"), DateTimeFormatter.ISO_DATE));

        return uploadSongDto;
    }

    @Transactional
    public String saveFile(MultipartFile file, UploadSongDto uploadSongDto) throws FileStorageException, IOException{
        String artistName = uploadSongDto.getArtist();
        String albumName = uploadSongDto.getAlbumName();

        makedir(Arrays.asList(artistName, albumName));

        Path targetPath = this.fileStorageLocation.resolve(StringUtils.cleanPath(artistName + "/" + albumName + "/" + file.getOriginalFilename()));

        if(Files.exists(targetPath)) {
            throw new FileStorageException("중복된 파일 이름 입니다.");
        }
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return targetPath.toString();
    }

    public void makedir(List<String> folderNames) throws IOException {
        StringBuilder dirPath = new StringBuilder("");
        for(String folderName : folderNames) {
            dirPath.append(StringUtils.cleanPath(folderName + "/"));
            Path targetFolder = this.fileStorageLocation.resolve(String.valueOf(dirPath));

            if(!Files.exists(targetFolder)) {
                Files.createDirectory(targetFolder);
            }
        }
    }

    @Transactional
    public Song saveSongInfo(MultipartFile uploadFile, Principal principal, String filePath,
                             String imagePath, String fileDownloadUri, UploadSongDto uploadSongDto) {
        Member currentUser = memberRepository.findOneByUserid(principal.getName()).get();
        Artist artist = currentUser.getArtist();

        String albumUrl = filePath.substring(filePath.lastIndexOf("/") + 1);

        ArtistChildId artistChildId = new ArtistChildId(artist.getId(), albumUrl);

        AlbumChildId albumChildId = new AlbumChildId(artistChildId, filePath);

        Album album = Album.builder()
                .albumName(uploadSongDto.getAlbumName())
                .artist(artist)
                .artistChildId(artistChildId)
                .genre(uploadSongDto.getGenre())
                .releaseDate(uploadSongDto.getReleaseDate())
                .photoUrl((imagePath != "") ? imagePath : null)
                .build();

        albumRepository.save(album);

        Song song = Song.builder()
                .songName(uploadSongDto.getSongName())
                .fileType(uploadFile.getContentType())
                .createTime(LocalDateTime.now())
                .downloadUri(fileDownloadUri)
                .genre(uploadSongDto.getGenre())
                .albumChildId(albumChildId)
                .size(uploadFile.getSize())
                .album(album)
                .build();

        return songRepository.save(song);
    }
}
