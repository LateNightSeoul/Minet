package com.Minet.Minet.controller;

import com.Minet.Minet.domain.statistic.Follow;
import com.Minet.Minet.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/add")
    public ResponseEntity addFollow(Principal principal, @RequestParam("artistId") Long artistId) {
        followService.addFollow(principal, artistId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<Follow> getFollowList(Principal principal) {
        return followService.getFollowList(principal);

    }
}
