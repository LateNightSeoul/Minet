package com.Minet.Minet.controller;

import com.Minet.Minet.dto.member.JoinDto;
import com.Minet.Minet.dto.member.LoginDto;
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

import static org.hamcrest.Matchers.is;
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

    private String jwt;

    @Test
    @Order(1)
    @DisplayName("join")
    void join() throws Exception {

        JoinDto joinDto = JoinDto.builder()
                .userName("lee")
                .email("lee@gamil.com")
                .password("1234")
                .phone("010-5612-2134")
                .authority(Authority.ROLE_MEMBER)
                .build();


        ResultActions result = mockMvc.perform(
                post("/join")
                        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(joinDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(handler().handlerType(AuthController.class))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is("lee")));

    }


    @Test
    @Order(2)
    @DisplayName("login")
    void login() throws Exception {

        LoginDto loginDto = LoginDto.builder()
                .email("lee@gamil.com")
                .password("1234")
                .build();

        ResultActions result = mockMvc.perform(
                post("/login")
                .content(objectMapper.writeValueAsString(loginDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AuthController.class))
                .andExpect(handler().methodName("login"));
    }
}