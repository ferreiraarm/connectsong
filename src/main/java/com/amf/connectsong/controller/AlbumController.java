package com.amf.connectsong.controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;





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
