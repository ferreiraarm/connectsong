package com.amf.connectsong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.dto.LoginDTO;
import com.amf.connectsong.dto.SignupDTO;
import com.amf.connectsong.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "ConnectSong")
public class AuthController {
    @Autowired
   private  AuthService authService;

    AuthController() {
    }

    @Operation(summary="Realiza Operações de validação de erro login", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping("")
    String login(@Valid @RequestBody LoginDTO loginRequest) {
        try {
            return authService.login(loginRequest);
        } catch (Exception e) {
            return "error";
        }
    }


    
    @Operation(summary="Realiza Operações para o login", method="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping("/user")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupDTO singUpRequest) {
        try {
            return authService.signUp(singUpRequest);
        } catch (Exception e) {
            if (e.getMessage() == "USERNAME_ALREADY_TAKEN") {
                return ResponseEntity.status(409).body("Error: Username is already taken!");
            }

            if (e.getMessage() == "EMAIL_ALREADY_TAKEN") {
                return ResponseEntity.status(409).body("Error: Email is already taken!");
            }

            if (e.getMessage() == "ROLE_NOT_FOUND") {
                return ResponseEntity.status(404).body("Error: Role not found!");
            }

            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}
