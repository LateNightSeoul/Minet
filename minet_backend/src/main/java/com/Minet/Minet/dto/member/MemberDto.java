package com.Minet.Minet.dto.member;

import com.Minet.Minet.domain.member.Artist;
import com.Minet.Minet.security.Authority;

import javax.persistence.*;
import java.time.LocalDateTime;

public class MemberDto {

    private Long id;

    private String userName;

    private String email;

    private boolean enabled;

    private LocalDateTime createTime;

    private Long loginCount;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
