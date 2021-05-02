package com.Minet.Minet.domain.music;

import com.Minet.Minet.domain.enumTypes.ChartGenre;
import com.Minet.Minet.domain.enumTypes.ChartType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Chart {

    @Id @GeneratedValue
    @Column(name = "chart_id")
    private Long id;

    private ChartType chartType;

    private ChartGenre chartGenre;

    private LocalDate chartDate;

    private LocalDateTime createTime;
}
