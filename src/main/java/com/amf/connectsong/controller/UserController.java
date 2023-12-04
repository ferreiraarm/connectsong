package com.amf.connectsong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amf.connectsong.dto.AddressDTO;
import com.amf.connectsong.dto.ReviewDTO;
import com.amf.connectsong.dto.ProfileVO;
import com.amf.connectsong.dto.ReviewVO;
import com.amf.connectsong.dto.UpdateProfileDTO;
import com.amf.connectsong.service.UserService;
import com.amf.connectsong.utils.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "ConnectSong User Controller")
public class UserController {
    @Autowired
    ExceptionHandler exceptionHandler;

    @Autowired
    UserService userService;

    @Operation(summary = "Busca de profiles", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileVO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        try {
            return userService.getProfile(token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Atualização do profiles", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileVO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDTO profile,
            @RequestHeader("Authorization") String token) {
        try {
            return userService.updateProfile(profile, token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Atualização e adição de endereços", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping("/address")
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO address,
            @RequestHeader("Authorization") String token) {
        try {
            return userService.addAddress(address, token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Busca de endereço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário ou endereço não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/address")
    public ResponseEntity<?> getAddress(@RequestHeader("Authorization") String token) {
        try {
            return userService.getUserAddress(token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Atualização e adição de reviews de albuns por id", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização feita ucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewVO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário ou Review ou album não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping("/review/{album_id}")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewDTO review,
            @RequestHeader("Authorization") String token, @PathVariable long album_id) {
        try {
            return userService.addReview(review, token, album_id);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @Operation(summary = "Busca de reviews", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewVO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário ou Review não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("/reviews")
    public ResponseEntity<?> getReviews(@RequestHeader("Authorization") String token) {
        try {
            return userService.getReviews(token);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @PostMapping("/profile/picture")
    public ResponseEntity<?> addProfilePicture(@RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file) {
        try {
            String filename = userService.addProfilePicture(token, file);

            return ResponseEntity.ok(filename);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

    @GetMapping("/profile/picture")
    public ResponseEntity<?> addProfilePicture(@RequestHeader("Authorization") String token,
            HttpServletRequest request) {
        try {

            Resource resource = userService.getProfilePicture(token);
            String contentType = "";

            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType.isBlank())
                contentType = "application/octet-stream";
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return exceptionHandler.returnException(e);
        }
    }

}
