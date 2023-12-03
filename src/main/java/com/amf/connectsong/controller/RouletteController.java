package com.amf.connectsong.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.AlbumDTO;
import com.amf.connectsong.model.Roulette;
import com.amf.connectsong.repository.RouletteRepository;
import com.amf.connectsong.service.RouletteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

@RestController
@RequestMapping("/api/roulette")
public class RouletteController implements Serializable {

    @Autowired
    private RouletteRepository repRouletteObj;

    @Autowired
    private RouletteService rouletteService;

    // @Operation(summary = "Busca todas as roletas", method = "GET")

    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Busca realizada com
    // sucesso"),
    // @ApiResponse(responseCode = "422", description = "Dados de requisição
    // inválida"),
    // @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    // @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos
    // dados"),
    // })
    // @GetMapping("/api/roulette")
    // public List<Roulette> selecionarTodasAsRoulettes(){
    // return repRouletteObj.findAll();
    // }

    /*
     * @GetMapping("/api/roulette/{User}")
     * public Roulette selecionarRoulettePorUser(@PathVariable User user){
     * 
     * return repRouletteObj.findByUser(user);
     * }
     */

    @Operation(summary = "Busca roletas por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/api/roulette/{id}")
    public Roulette selecionarRoulettePorId(@PathVariable long id) {
        return repRouletteObj.findById(id);
    }

    @Operation(summary = "Gira a roleta", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/api/roulette/spin")
    public ResponseEntity<AlbumDTO> spinRoulette(@RequestHeader("Authorization") String token) {

        return rouletteService.spinRoulette(token);
        // return repRouletteObj.spinRoulette();
    }

}
