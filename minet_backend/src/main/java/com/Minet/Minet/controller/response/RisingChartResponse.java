package com.Minet.Minet.controller.response;

import com.Minet.Minet.domain.enumTypes.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RisingChartResponse {

    private String songUrl;

    private String albumUrl;

    private Long artistId;

    private Long rank;

    private String songName;

    private String artistName;

    private Genre genre;

}
