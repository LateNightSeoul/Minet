package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.RisingChartResponse;
import com.Minet.Minet.domain.music.ChartSong;
import com.Minet.Minet.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("/rising")
    public List<ChartSong> getRisingChart() throws IllegalAccessException {
        List<ChartSong> risingChart = chartService.getRisingChart();
        return risingChart;
    }
}
