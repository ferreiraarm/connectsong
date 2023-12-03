package com.amf.connectsong.controller;

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

@RestController
@RequestMapping("/api/roulette")
public class RouletteController {

    @Autowired
    private RouletteService rouletteService;

    @Operation(summary = "Gira a roleta para pegar um album aleatório", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso, album retornado."),
            @ApiResponse(responseCode = "404", description = "Not found user!"),
            @ApiResponse(responseCode = "404", description = "Not found token!"),
            @ApiResponse(responseCode = "404", description = "Not found roulette!"),
            @ApiResponse(responseCode = "404", description = "Not found albums!"),
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
