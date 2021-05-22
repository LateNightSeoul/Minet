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
import java.util.List;
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
    public void testWhole() throws Exception {
        createChartInsertData();
        createChart();
        testSubQuery();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createChartInsertData() throws Exception {
        for (int i = 65; i < 91; i++) {

            Artist artist = new Artist();
            artist.setArtistName(("가수"));
            artistRepository.save(artist);

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

            int upperInt = random.nextInt(100);

            for (int j = 0; j < upperInt + random.nextInt(100); j++) {
                SongLikeId songLikeId1 = new SongLikeId(albumChildId, LocalDateTime.now().minusSeconds(random.nextInt(10000)));

                SongLike songLike = new SongLike();
                songLike.setSong(song);
                songLike.setSongLikeId(songLikeId1);

                songLikeRepository.save(songLike);

                em.flush();
            }

            for (int j = 0; j < upperInt; j++) {
                SongLikeId songLikeId2 = new SongLikeId(albumChildId, LocalDateTime.now().minusDays(1).minusSeconds(random.nextInt(10000)));

                SongLike songLike2 = new SongLike();
                songLike2.setSong(song);
                songLike2.setSongLikeId(songLikeId2);

                songLikeRepository.save(songLike2);

                em.flush();
            }
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createChart() throws Exception{
        List<Object[]> songLike_yesterday = em.createNativeQuery("SELECT SL.SONG_URL, COUNT(SL.SONG_URL) FROM SONG_LIKE AS SL " +
                "WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate, 'yyyy-MM-dd') GROUP BY SL.SONG_URL" )
                .setParameter("localdate", LocalDateTime.now().minusDays(1))
                .getResultList();

        List<Object[]> songLike_today = em.createNativeQuery("SELECT SL.SONG_URL, COUNT(SL.SONG_URL) FROM SONG_LIKE AS SL " +
                "WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate, 'yyyy-MM-dd') GROUP BY SL.SONG_URL")
                .setParameter("localdate", LocalDateTime.now())
                .getResultList();

        List<Object[]> visited_yesterday = em.createNativeQuery("SELECT DV.SONG_URL, DV.COUNT FROM DAILY_VISITED AS DV " +
                "WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate, 'yyyy-MM-dd')")
                .setParameter("localdate", LocalDateTime.now().minusDays(1))
                .getResultList();

        List<Object[]> visited_today = em.createNativeQuery("SELECT DV.SONG_URL, DV.COUNT FROM DAILY_VISITED AS DV " +
                "WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate, 'yyyy-MM-dd')")
                .setParameter("localdate", LocalDateTime.now())
                .getResultList();

        System.out.println(songLike_yesterday);
        for(Object[] s : songLike_yesterday) {
            for(int i = 0; i < s.length; i++) {
                System.out.println(s[i]);
            }
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testSubQuery() {
        List<Object[]> songLike = em.createNativeQuery("SELECT S1.SONG_URL, (S1.CNT - S2.CNT) AS LIKE_INC, S1.CNT AS CNT1, S2.CNT AS CNT2 " +
                "FROM (SELECT SL.SONG_URL, COUNT(SL.SONG_URL) AS CNT FROM SONG_LIKE SL WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate1, 'yyyy-MM-dd') GROUP BY SL.SONG_URL) AS S1," +
                "(SELECT SL.SONG_URL, COUNT(SL.SONG_URL) AS CNT FROM SONG_LIKE SL WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate2, 'yyyy-MM-dd') GROUP BY SL.SONG_URL) AS S2 " +
                "WHERE S1.SONG_URL = S2.SONG_URL ORDER BY S1.SONG_URL ASC")
                .setParameter("localdate1", LocalDateTime.now())
                .setParameter("localdate2", LocalDateTime.now().minusDays(1))
                .getResultList();

        for(Object[] s : songLike) {
            System.out.println("SONG_URL : " + s[0] + "  LIKE_INC : " + s[1] + " REST : " + s[2] + " " + s[3]);
        }

        List<Object[]> visited = em.createNativeQuery("SELECT V1.SONG_URL, (V1.COUNT - V2.COUNT) AS CNT " +
                "FROM (SELECT DV.SONG_URL, DV.COUNT FROM DAILY_VISITED AS DV WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate1, 'yyyy-MM-dd') GROUP BY DV.SONG_URL) AS V1," +
                "(SELECT DV.SONG_URL, DV.COUNT FROM DAILY_VISITED AS DV WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate2, 'yyyy-MM-dd') GROUP BY DV.SONG_URL) AS V2 " +
                "WHERE V1.SONG_URL = V2.SONG_URL ORDER BY V1.SONG_URL ASC")
                .setParameter("localdate1", LocalDateTime.now())
                .setParameter("localdate2", LocalDateTime.now().minusDays(1))
                .getResultList();

        System.out.println("***********************************************************");

        for(Object[] a : visited) {
            System.out.println("Song_URL : " + a[0] + " CNT : " + a[1]);
        }
    }
}
