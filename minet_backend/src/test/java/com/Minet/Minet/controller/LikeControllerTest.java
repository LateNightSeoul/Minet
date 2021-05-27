package com.Minet.Minet.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikeControllerTest extends AbstractControllerTest{

    @Autowired
    private LikeController likeController;

    @Override
    protected Object controller() {
        return likeController;
    }

    @Test
    public void addSongLike() throws Exception {

        MultiValueMap<String, String> infos = new LinkedMultiValueMap<>();

        mockMvc.perform(post("/like/addSongLike")).andExpect(status().isOk());
    }
}
