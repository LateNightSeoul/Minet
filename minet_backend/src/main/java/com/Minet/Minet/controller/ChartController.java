package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.RisingChartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chart")
public class ChartController {

    @GetMapping("/rising")
    public ResponseEntity<RisingChartResponse> getRisingChart() {



        return new ResponseEntity<>(new RisingChartResponse(), HttpStatus.OK);
    }
}
