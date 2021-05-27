package com.Minet.Minet.controller;

import com.Minet.Minet.es.SongESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SongESService songESService;

    @GetMapping("/song")
    public List<Map<String, Object>> search(@RequestParam("keyword") String keyword, @RequestParam("type") String type, Pageable pageable) throws IOException {
        return songESService.search(keyword, type, pageable);
    }
}
