package com.amf.connectsong.controller;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.amf.connectsong.model.Message;
import com.amf.connectsong.repository.MessageRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/message")
@Tag(name = "ConnectSong Message Controller")
public class MessageController implements Serializable {
    
    @Autowired
    private MessageRepository repMessageObj;

     @Operation(summary = "Busca todas as menagens", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            /*Colocar aqui o dot schema */
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("api/message")
    public List<Message> selecionarTodasMessage(){
        return repMessageObj.findAll();
    }

    @Operation(summary = "Busca dados de mensagens por id", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            /*Colocar aqui o dto schema */
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("api/message/{id}")
    public Message selecionarMessagePorId(@PathVariable long id){
        return repMessageObj.findById(id);
    }
/*
    @PostMapping("/api/message/cadastro")
    public Message enviarMessage(@RequestBody Message objMessage){
        return repMessageObj.save(objMessage);
    }
    */

    @Operation(summary = "deleta mensagens por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado"),
            /*Colocar aqui o dot schema */
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar"),
    })
    @DeleteMapping("/api/message/{id}")
    public void delete(@PathVariable long id){
        Message mobj = selecionarMessagePorId(id);
        repMessageObj.delete(mobj);
    }
}
