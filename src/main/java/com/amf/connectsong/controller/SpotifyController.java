package com.amf.connectsong.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amf.connectsong.model.ESpotiftKeys;
import com.amf.connectsong.service.SpotifyService;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
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
                .scope("user-read-private, user-read-email, user-top-read")
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

}