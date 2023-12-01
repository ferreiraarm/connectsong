package com.amf.connectsong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.Album;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AlbumController {
    @GetMapping("/api/album")
    public String getUsers() {
        return "varios albums";
    }

    @GetMapping("/api/album/test")
    public String getUsersTest() {
        return "testando o hot swapping";
    }
   
}
