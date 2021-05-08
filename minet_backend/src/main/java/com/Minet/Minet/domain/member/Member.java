package com.Minet.Minet.domain.member;

import com.Minet.Minet.security.Authority;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private String username;

    private String userid;

    private String password;

    private String phone;

    private boolean enabled;

    private LocalDateTime createTime;

    private Authority role;

}
