package com.api.expo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.expo.models.Artwork;
import com.api.expo.services.ArtworkService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ArtworkController {
    @Autowired
    private ArtworkService artworkService;

    @PostMapping("/add_artwork")
    public Artwork add_Artwork(@RequestParam("images") List<MultipartFile> images, @RequestBody Artwork artwork)
            throws IOException {
        return this.artworkService.add_artwork(images);
    }

}
