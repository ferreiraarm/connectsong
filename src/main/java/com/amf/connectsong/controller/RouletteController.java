package com.amf.connectsong.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.service.RouletteService;
import com.amf.connectsong.utils.ExceptionHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/roulette")
@Tag(name = "ConnectSong Roulette Controller")
public class RouletteController implements Serializable {
    
    @Autowired
    private RouletteService rouletteService;

    @Operation(summary = "Gira a roleta para pegar um album aleatório", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, album retornado."),
            /*Colocar aqui o dot schema */
            @ApiResponse(responseCode = "404", description = "Usuário ou token ou roulette ou albuns não encontrados!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/spin")
    public ResponseEntity<?> spinRoulette(@RequestHeader("Authorization") String token) {
        try {
            return rouletteService.spinRoulette(token);
        } catch (Exception e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.returnException(e);
        }
    }

}
