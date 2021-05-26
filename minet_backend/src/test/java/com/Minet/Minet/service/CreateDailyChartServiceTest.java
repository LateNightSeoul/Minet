package com.Minet.Minet.service;

import com.Minet.Minet.domain.enumTypes.ChartType;
import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.domain.music.Album;
import com.Minet.Minet.domain.music.Chart;
import com.Minet.Minet.domain.music.ChartSong;
import com.Minet.Minet.domain.music.Song;
import com.Minet.Minet.domain.music.ids.AlbumChildId;
import com.Minet.Minet.domain.music.ids.ArtistChildId;
import com.Minet.Minet.domain.music.ids.SongChildId;
import com.Minet.Minet.domain.music.ids.SongLikeId;
import com.Minet.Minet.domain.statistic.DailyVisited;
import com.Minet.Minet.domain.statistic.SongLike;
import com.Minet.Minet.domain.statistic.TotalVisited;
import com.Minet.Minet.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateDailyChartServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TotalVisitedRepository totalVisitedRepository;

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
    ChartRepository chartRepository;

    @Autowired
    ChartSongRepository chartSongRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void testWhole() throws Exception {
        createChartInsertData();
        testSubQuery();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createChartInsertData() throws Exception {
        for (int i = 0; i < 25; i++) {

            Random random = new Random();

            Artist artist = new Artist();
            artist.setArtistName(("가수"));
            artistRepository.save(artist);

            ArtistChildId artistChildId = new ArtistChildId(artist.getId(), "/path/album");
            Album album = new Album();
            album.setArtistChildId(artistChildId);
            album.setArtist(artist);
            album.setAlbumName(("album" + i));
            albumRepository.save(album);

            AlbumChildId albumChildId = new AlbumChildId(artistChildId, String.valueOf("/path" + i + 10000));
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

            SongLikeId sli1 = new SongLikeId(albumChildId, LocalDateTime.now());
            SongLikeId sli2 = new SongLikeId(albumChildId, LocalDateTime.now().minusDays(1));

            long dv_upper1 = random.nextInt(10000);
            long dv_upper2 = random.nextInt(10000);

            DailyVisited dailyVisited = new DailyVisited();
            dailyVisited.setCount(dv_upper1);
            dailyVisited.setSongLikeId(sli1);
            dailyVisited.setSong(song);
            dailyVisitedRepository.save(dailyVisited);

            DailyVisited dailyVisited2 = new DailyVisited();
            dailyVisited2.setCount(dv_upper2);
            dailyVisited2.setSongLikeId(sli2);
            dailyVisited2.setSong(song);
            dailyVisitedRepository.save(dailyVisited2);

            SongChildId songChildId = new SongChildId(albumChildId);

            TotalVisited totalVisited = new TotalVisited();
            totalVisited.setSongChildId(songChildId);
            totalVisited.setCount(dv_upper1 + dv_upper2);
            totalVisited.setSong(song);
            totalVisited.setCreateDate(LocalDateTime.now());
            totalVisitedRepository.save(totalVisited);

            em.flush();

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
        for(Object[] s : visited_today) {
            for(int i = 0; i < s.length; i++) {
                System.out.println(s[i]);
            }
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testSubQuery() {
        List<Object[]> songLike = em.createNativeQuery("SELECT S1.SONG_URL, S1.ALBUM_URL, S1.ARTIST_ID, (S1.CNT - S2.CNT) AS LIKE_INC, (SELECT COUNT(TL.SONG_URL) FROM SONG_LIKE TL WHERE TL.SONG_URL = S1.SONG_URL) AS TOTAL_CNT " +
                "FROM (SELECT SL.SONG_URL, SL.ALBUM_URL, SL.ARTIST_ID, COUNT(SL.SONG_URL) AS CNT FROM SONG_LIKE SL WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate1, 'yyyy-MM-dd') GROUP BY SL.SONG_URL) AS S1," +
                "(SELECT SL.SONG_URL, COUNT(SL.SONG_URL) AS CNT FROM SONG_LIKE SL WHERE FORMATDATETIME(SL.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate2, 'yyyy-MM-dd') GROUP BY SL.SONG_URL) AS S2 " +
                "WHERE S1.SONG_URL = S2.SONG_URL ORDER BY S1.SONG_URL ASC")
                .setParameter("localdate1", LocalDateTime.now())
                .setParameter("localdate2", LocalDateTime.now().minusDays(1))
                .getResultList();

        List<Object[]> visited = em.createNativeQuery("SELECT V1.SONG_URL, V1.ALBUM_URL, V1.ARTIST_ID, (V1.COUNT - V2.COUNT) AS CNT, (SELECT TV.COUNT FROM TOTAL_VISITED TV WHERE TV.SONG_URL = V1.SONG_URL) AS TOTAL_CNT " +
                "FROM (SELECT DV.SONG_URL, DV.ALBUM_URL, DV.ARTIST_ID, DV.COUNT FROM DAILY_VISITED AS DV WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate1, 'yyyy-MM-dd') GROUP BY DV.SONG_URL) AS V1," +
                "(SELECT DV.SONG_URL, DV.COUNT FROM DAILY_VISITED AS DV WHERE FORMATDATETIME(DV.CREATE_DATE, 'yyyy-MM-dd') = FORMATDATETIME(:localdate2, 'yyyy-MM-dd') GROUP BY DV.SONG_URL) AS V2 " +
                "WHERE V1.SONG_URL = V2.SONG_URL ORDER BY V1.SONG_URL ASC")
                .setParameter("localdate1", LocalDateTime.now())
                .setParameter("localdate2", LocalDateTime.now().minusDays(1))
                .getResultList();


        for (Object[] s : songLike) {
            for(int i = 0; i < s.length; i++) {
                System.out.println(s[i]);
            }
        }

        HashMap<List, Long> scoreResultMap = new HashMap<>();

        for (int i = 0; i < songLike.size(); i++) {
            for (int j = 0; j < visited.size(); j++) {
                if(songLike.get(i)[0] == visited.get(j)[0]) {
                    Object[] sl = songLike.get(i);
                    Object[] vs = visited.get(i);
                    String song_url = (String) sl[0];
                    String album_url = (String) sl[1];
                    long artist_id = ((BigInteger)sl[2]).longValue();

                    long song_cnt = ((BigInteger)sl[3]).longValue();
                    long total_song_cnt = ((BigInteger)sl[4]).longValue();
                    long visited_cnt = ((BigInteger)vs[3]).longValue();
                    long total_visited_cnt = ((BigInteger)vs[4]).longValue();
                    long score = (long) ((song_cnt * 10 + visited_cnt) / Math.sqrt(total_song_cnt + total_visited_cnt + 1));
                    scoreResultMap.put(Arrays.asList(artist_id, album_url, song_url), score);
                }
            }
        }

        List<Map.Entry<List, Long>> scoreResult = new ArrayList<>(scoreResultMap.entrySet());


        Collections.sort(scoreResult, new Comparator<Map.Entry<List, Long>>() {
            @Override
            public int compare(Map.Entry<List, Long> o1, Map.Entry<List, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println(scoreResult);

        // Service 기능 구현

        Chart risingChart = new Chart();
        risingChart.setChartDate(LocalDate.now());
        risingChart.setChartType(ChartType.Rising);
        risingChart.setCreateTime(LocalDateTime.now());
        chartRepository.save(risingChart);

        for(int i = 0; i < 100; i++) {
            if (i >= scoreResult.size()) {
                break;
            }
            List info_list = scoreResult.get(i).getKey();
            ArtistChildId artistChildId = new ArtistChildId((Long) info_list.get(0), (String) info_list.get(1));
            AlbumChildId albumChildId = new AlbumChildId(artistChildId, (String) info_list.get(2));
            Song findSong = songRepository.findByAlbumChildId(albumChildId);

            SongChildId songChildId = new SongChildId(albumChildId);

            ChartSong chartSong = new ChartSong();
            chartSong.setChart(risingChart);
            chartSong.setSong(findSong);
            chartSong.setSongChildId(songChildId);
            chartSong.setSongName(findSong.getSongName());
            chartSong.setGenre(findSong.getGenre());
            chartSong.setRank(Long.valueOf(i + 1));
            Optional<Artist> artist = artistRepository.findById(chartSong.getSongChildId().getAlbumChildId().getArtistChildId().getArtist_id());
            chartSong.setArtistName(artist.get().getArtistName());

            chartSongRepository.save(chartSong);

        }
    }
}
