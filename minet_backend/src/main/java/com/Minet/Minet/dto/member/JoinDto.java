package com.Minet.Minet.dto.member;

import com.Minet.Minet.domain.enumTypes.Genre;
import com.Minet.Minet.security.Authority;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class JoinDto {

    private String username;

    private String userid;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String artistName;

    private Genre genre;

    private LocalDate birth;

    private String profileUrl;
}
