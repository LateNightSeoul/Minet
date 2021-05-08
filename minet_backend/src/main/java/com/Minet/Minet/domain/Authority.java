package com.Minet.Minet.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class Authority {

    @Id
    @Column(length = 50)
    private String authorityName;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
