package com.Minet.Minet.controller;

import com.Minet.Minet.dto.member.JoinDto;
import com.Minet.Minet.security.Authority;
import com.Minet.Minet.security.WithMockJwtAuthentication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @Order(1)
    @DisplayName("회원 가입")
    void join() throws Exception {

        JoinDto joinDto = JoinDto.builder()
                .username("lee")
                .email("lee@gamil.com")
                .password("1234")
                .phone("010-5612-2134")
                .authority(Authority.ROLE_MEMBER)
                .build();


        ResultActions result = mockMvc.perform(
                post("/join")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(joinDto))
        );
        result.andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @Order(2)
    @WithMockJwtAuthentication
    @DisplayName("로그인 (토근이 올바른 경우)")
    void login() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/login")
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AuthController.class))
                .andExpect(handler().methodName("login"));

    }
}