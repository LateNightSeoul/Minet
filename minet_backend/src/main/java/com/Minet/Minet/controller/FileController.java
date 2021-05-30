package com.Minet.Minet.controller;
import com.Minet.Minet.controller.response.UploadSongResponse;
import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.dto.file.UploadAlbumDto;
import com.Minet.Minet.dto.file.UploadSongInfoDto;
import com.Minet.Minet.dto.file.UploadSongInfoWrapperDto;
import com.Minet.Minet.es.SongESService;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.repository.MemberRepository;
import com.Minet.Minet.repository.SongRepository;
import com.Minet.Minet.security.Authority;
import com.Minet.Minet.service.FileService;
import com.Minet.Minet.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final MemberService memberService;
    private final SongESService songESService;

    @SneakyThrows
    @PostMapping("/upload/album")
    public ResponseEntity<UploadSongResponse> uploadAlbum(@RequestParam("song") MultipartFile[] uploadSongs,
                                                          @RequestParam("songInfos") String[] songInfoStrings,
                                                          @RequestParam("image") MultipartFile uploadImage,
                                                          Principal principal)  {

        UploadSongInfoWrapperDto songInfos = new UploadSongInfoWrapperDto();

        for (String songInfo : songInfoStrings) {
            JSONObject jsonObject = new JSONObject(songInfo);
            UploadSongInfoDto uploadSongInfoDto = UploadSongInfoDto.builder().songName(jsonObject.getString("songName"))
                    .albumName(jsonObject.getString("albumName"))
                    .songNumber(jsonObject.getInt("songNumber"))
                    .releaseDate(LocalDate.parse(jsonObject.getString("releaseDate"), DateTimeFormatter.ISO_DATE))
                    .artist(jsonObject.getString("artist"))
                    .genre(Genre.fromString(jsonObject.getString("genre")))
                    .fileName(jsonObject.getString("fileName")).build();
            songInfos.getUploadSongInfoDto().add(uploadSongInfoDto);
        }

        try {
            String imagePath = null;
            Member currentUser = memberService.findByUserId(principal.getName()).get();
            String dirPath = fileService.makedir(Arrays.asList(songInfos.getUploadSongInfoDto().get(0).getArtist(), songInfos.getUploadSongInfoDto().get(0).getAlbumName()));

            if(!uploadImage.isEmpty()) {
                imagePath = fileService.saveFile(uploadImage, dirPath);
            }

            Album albumSaved = fileService.saveAlbumInfo(currentUser, songInfos.getUploadSongInfoDto().get(0), imagePath);

            for(UploadSongInfoDto songInfo : songInfos.getUploadSongInfoDto()) {
                for(MultipartFile song : uploadSongs) {
                    if(songInfo.getFileName().equals(song.getOriginalFilename())) {
                        String songPath = fileService.saveFile(song, dirPath);
                        fileService.saveSongInfo(albumSaved, currentUser, songInfo, songPath, song);
                        songESService.save(dirPath, songPath, imagePath, songInfo);
                    }
                }
            }
            return new ResponseEntity<UploadSongResponse>(new UploadSongResponse("앨범 업로드 성공", albumSaved.getAlbumName()), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileStorageException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public void downloadMusic(@RequestParam("fileUrl") String songUrl,
                        HttpServletResponse response) throws IOException {

        File filePath = new File(URLDecoder.decode(songUrl, "UTF-8"));
        Long startRange = 0L;
        Long endRange = filePath.length();

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
            ServletOutputStream sos = response.getOutputStream();){

            Integer bufferSize = 1024, data = 0;
            byte[] b = new byte[bufferSize];
            Long count = startRange;

            randomAccessFile.seek(startRange);

            while(true) {
                data = randomAccessFile.read(b, 0, b.length);

                if(count <= endRange) {
                    sos.write(b, 0, data);
                    count += bufferSize;
                    randomAccessFile.seek(count);
                } else {
                    break;
                }
            }
            sos.flush();
        }
    }

}
