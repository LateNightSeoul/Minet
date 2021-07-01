package com.Minet.Minet.domain.member;

import com.Minet.Minet.security.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private Artist artist;

    private String phone;

    private boolean enabled;

    private Long loginCount;

    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    private Authority authority;

}
