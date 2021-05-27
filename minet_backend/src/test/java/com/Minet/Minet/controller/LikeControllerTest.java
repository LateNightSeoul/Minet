package com.Minet.Minet.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LikeController likeController;

    @Test
    public void addSongLike() throws Exception {

        MultiValueMap<String, String> infos = new LinkedMultiValueMap<>();

        infos.add("song_url", "/path010000");
        infos.add("album_url", "/path/album");
        infos.add("artist_id", "1");

        mockMvc.perform(post("/like/addSongLike").params(infos)).andExpect(status().isOk());
    }
}
