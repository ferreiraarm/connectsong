package com.amf.connectsong.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.MessageDTO;
import com.amf.connectsong.dto.MessageVO;
import com.amf.connectsong.service.MessageService;
import com.amf.connectsong.utils.ExceptionHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/message")
@Tag(name = "ConnectSong Message Controller")
public class MessageController implements Serializable {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Operation(summary = "Busca todas as menagens", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageVO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/{username}")
    public ResponseEntity<?> getAllMessages(@RequestHeader("Authorization") String token,
            @PathVariable String username) {
        return messageService.getMessages(token, username);
    }

    @Operation(summary = "Envia mensagem para um usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso."),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping("/save/{username}")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDTO message,
            @RequestHeader("Authorization") String token,
            @PathVariable String username) {
        try {
            return messageService.saveMessage(message, token, username);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Deleta mensagens por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem deletada!"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar"),
    })
    @DeleteMapping("/api/message/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            return messageService.deleteMessage(id, token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }
}
