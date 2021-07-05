package com.Minet.Minet.dto;

import com.Minet.Minet.domain.member.Member;
import com.Minet.Minet.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationDto {

    private String token;

    private MemberDto memberDto;

    public AuthenticationDto() {

    }

}
