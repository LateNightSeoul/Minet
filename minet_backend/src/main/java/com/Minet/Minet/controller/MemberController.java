package com.Minet.Minet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity login() {
        return new ResponseEntity(DefaultRes.res(200, "good", "good"), HttpStatus.OK);

    }
}
