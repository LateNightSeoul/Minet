package com.Minet.Minet.service;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.domain.music.ids.SongChildId;
import com.Minet.Minet.domain.music.ids.SongLikeId;
import com.Minet.Minet.domain.statistic.DailyVisited;
import com.Minet.Minet.domain.statistic.SongLike;
import com.Minet.Minet.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateDailyChartServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    DailyVisitedRepository dailyVisitedRepository;

    @Autowired
    SongLikeRepository songLikeRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void createChart() throws Exception {
        for (int i = 65; i < 91; i++) {

            Artist artist = new Artist();
            artist.setArtistName(("가수"));
            artistRepository.save(artist);

            System.out.println(artist.getId());

            System.out.println("fuck!!!!!!!!!!!!!");

            ArtistChildId artistChildId = new ArtistChildId(artist.getId(), String.valueOf(i));
            Album album = new Album();
            album.setArtistChildId(artistChildId);
            album.setArtist(artist);
            album.setAlbumName(("album" + i));
            albumRepository.save(album);

            AlbumChildId albumChildId = new AlbumChildId(artistChildId, String.valueOf(i + 10000));
            Song song = Song.builder()
                    .songName(String.valueOf(i))
                    .fileType((String.valueOf(i) + ".mp3"))
                    .createTime(LocalDateTime.now())
                    .genre(Genre.HIPHOP)
                    .albumChildId(albumChildId)
                    .size(1313l)
                    .album(album)
                    .songNumber(1)
                    .build();
            songRepository.save(song);

            em.flush();

            SongChildId songChildId = new SongChildId(albumChildId);
            Random random = new Random();

            DailyVisited dailyVisited = new DailyVisited();

            dailyVisited.setCount((long) random.nextInt(10000));
            dailyVisited.setSongChildId(songChildId);
            dailyVisited.setSong(song);
            dailyVisited.setCreateDate(LocalDateTime.now());

            dailyVisitedRepository.save(dailyVisited);

            DailyVisited dailyVisited2 = new DailyVisited();

            dailyVisited2.setCount((long) random.nextInt(10000));
            dailyVisited2.setSongChildId(songChildId);
            dailyVisited2.setSong(song);
            dailyVisited2.setCreateDate(LocalDateTime.now().minusDays(1));

            dailyVisitedRepository.save(dailyVisited2);

            for (int j = 0; j < random.nextInt(1000); j++) {
                SongLikeId songLikeId1 = new SongLikeId(albumChildId, LocalDateTime.now().minusSeconds(random.nextInt(10000)));
                SongLikeId songLikeId2 = new SongLikeId(albumChildId, LocalDateTime.now().minusDays(1).minusSeconds(random.nextInt(10000)));

                SongLike songLike = new SongLike();
                songLike.setSong(song);
                songLike.setSongLikeId(songLikeId1);

                songLikeRepository.save(songLike);

                SongLike songLike2 = new SongLike();
                songLike2.setSong(song);
                songLike2.setSongLikeId(songLikeId2);

                songLikeRepository.save(songLike2);

                em.flush();
            }



        }
    }
}
