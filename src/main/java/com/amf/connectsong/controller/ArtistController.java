package com.amf.connectsong.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.Artist;
import com.amf.connectsong.repository.ArtistRepository;

@RestController
public class ArtistController {
    
    @Autowired
    private ArtistRepository repArtistObj;



    @GetMapping("/api/artist/{id}")
    public Artist selecionarArtistPorID(@PathVariable long id){
        return repArtistObj.findById(id);
    }
    
    @GetMapping("/api/artist/{nome}")
    public Artist selecionarArtistPorNome(@PathVariable String nome){
        return repArtistObj.findByNome(nome);
    }



}
