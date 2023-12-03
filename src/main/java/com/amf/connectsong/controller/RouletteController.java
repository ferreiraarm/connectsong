package com.amf.connectsong.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.Roulette;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.RouletteRepository;

@RestController
public class RouletteController {
    
    @Autowired
    private RouletteRepository repRouletteObj;

    @GetMapping("/api/roulette")
    public List<Roulette> selecionarTodasAsRoulettes(){
        return repRouletteObj.findAll();
    }

    @GetMapping("/api/roulette/{User}")
    public Roulette selecionarRoulettePorUser(@PathVariable User user){

        return repRouletteObj.findByUser(user);
    }

    @GetMapping("/api/roulette/{id}")
    public Roulette selecionarRoulettePorId(@PathVariable long id){
        return repRouletteObj.findById(id);
    }

}
