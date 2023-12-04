package com.amf.connectsong.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.ArtistDTO;
import com.amf.connectsong.model.Artist;
import com.amf.connectsong.repository.ArtistRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/artist")
@Tag(name = "ConnectSong Artist Controller")
public class ArtistController implements Serializable{
    
    @Autowired
    private ArtistRepository repArtistObj;

    @Operation(summary = "Busca dados de artistas por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistDTO.class)) }),          
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/api/artist/{id}")
    public Artist selecionarArtistPorID(@PathVariable long id) {
        return repArtistObj.findById(id);
    }

}
