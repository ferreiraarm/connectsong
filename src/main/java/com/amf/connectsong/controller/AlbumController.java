package com.amf.connectsong.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.Album;
import com.amf.connectsong.repository.AlbumRepository;







@RestController
public class AlbumController {

    @Autowired
    private AlbumRepository repAlbumObj;

    @GetMapping("/api/album")
    public List<Album> selecionarTodosOsAlbuns() {
       return repAlbumObj.findAll();

    }

    @GetMapping("/api/album/{id}")
    public Album selecionarAlbumPorId(@PathVariable long id) {
        return repAlbumObj.findById(id);
    }
   
    @GetMapping("/api/album/{nome}")
    public Album selecionarAlbumPorNome(@PathVariable String nome){
    
        return repAlbumObj.findByNome(nome);
        

    }
}
