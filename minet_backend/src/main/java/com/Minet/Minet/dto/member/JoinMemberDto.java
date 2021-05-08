package com.Minet.Minet.dto.member;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class JoinMemberDto {

    private String username;

    private String userid;

    private String password;

    private String phone;

    private boolean isArtist;



}
