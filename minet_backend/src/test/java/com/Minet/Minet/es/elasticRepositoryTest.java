package com.Minet.Minet.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class elasticRepositoryTest {

    @Resource
    SongESRepository songESRepository;

    @Test
    public void testMember() throws Exception {
        Song song = new Song();
        song.setId("1");
        song.setArtist("sik-k");
        song.setLyric("바람이 불어 나를 밀어. 그녀에게로 가래 to see her.");
        song.setTitle("new Love");
        songESRepository.save(song);
    }
}
