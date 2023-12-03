package com.amf.connectsong.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.ESpotiftKeys;
import com.amf.connectsong.service.SpotifyService;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {
    @Autowired
    SpotifyService spotifyService;

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/spotify/callback");
    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(ESpotiftKeys.CLIENT_ID.getValue())
            .setClientSecret(ESpotiftKeys.CLIENT_SECRET.getValue())
            .setRedirectUri(redirectUri)
            .build();

    @PostMapping("/login")
    @ResponseBody
    public String login() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read, user-library-read")
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String userCode,
            @RequestHeader("Authorization") String token) {

        this.code = userCode;

        Logger logger = LoggerFactory.getLogger(AuthController.class);
        logger.info(token);

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            return spotifyService.getUserAlbums(spotifyApi, token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getStackTrace());
        }
    }

}