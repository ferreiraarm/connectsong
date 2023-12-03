package com.amf.connectsong.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {
    public ResponseEntity<?> returnException(Exception e) {
        switch (e.getMessage()) {
            case "EMAIL_ALREADY_TAKEN":
                return new ResponseEntity<>("Error: Email is already taken!", HttpStatus.CONFLICT);

            case "USERNAME_ALREADY_TAKEN":
                return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.CONFLICT);

            case "ROLE_NOT_FOUND":
                return new ResponseEntity<>("Error: Role not found!", HttpStatus.NOT_FOUND);

            case "USER_NOT_FOUND":
                return new ResponseEntity<>("Error: User not found!", HttpStatus.NOT_FOUND);

            case "NO_ALBUMS_FOUND":
                return new ResponseEntity<>("Error: Album not found!", HttpStatus.NOT_FOUND);

            case "TOKEN_NOT_FOUND":
                return new ResponseEntity<>("Token not found!", HttpStatus.NOT_FOUND);

            case "ROULETTE_NOT_FOUND":
                return new ResponseEntity<>("Roulette not found!", HttpStatus.NOT_FOUND);

            default:
                break;
        }

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
