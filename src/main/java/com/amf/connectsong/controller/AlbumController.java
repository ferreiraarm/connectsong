package com.amf.connectsong.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.AlbumDTO;
import com.amf.connectsong.service.AlbumService;
import com.amf.connectsong.utils.ExceptionHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/album")
@Tag(name = "ConnectSong Album Controller")
public class AlbumController implements Serializable {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Operation(summary = "Busca todos os  dados so album ", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found user or token or albums!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("")
    public ResponseEntity<?> getAllUserAlbums(@RequestHeader("Authorization") String token) {
        try {
            return albumService.getAlbums(token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }

    }

    @Operation(summary = "Busca dados de albuns por id", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Album not found!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("{id}")
    public ResponseEntity<?> selecionarAlbumPorId(@PathVariable long id) {
        try {
            return albumService.getAlbumById(id);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

}
