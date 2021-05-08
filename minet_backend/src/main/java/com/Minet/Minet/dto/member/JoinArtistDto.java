package com.Minet.Minet.dto.member;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class JoinArtistDto {

    private String username;

    private String userid;

    private String password;

    private String phone;

    private boolean isArtist;

    private String artistName;

    private Genre genre;

    private LocalDate birth;

    private String profileUrl;
}
