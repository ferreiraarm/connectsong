package com.amf.connectsong.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.AlbumVO;
import com.amf.connectsong.dto.ArtistDTO;
import com.amf.connectsong.dto.ReviewVO;
import com.amf.connectsong.model.Album;
import com.amf.connectsong.model.User;
import com.amf.connectsong.repository.AlbumRepository;
import com.amf.connectsong.repository.UserRepository;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> getAlbums(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        if (token == null) {
            throw new RuntimeException("TOKEN_NOT_FOUND");
        }

        Optional<User> currentUser = userRepository.findByUsername(username);

        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Album[] albums = albumRepository.findAllByRoulette(currentUser.get().getRoulette());

        if (albums.length == 0) {
            throw new RuntimeException("NO_ALBUMS_FOUND");
        }

        Set<AlbumVO> albumDTOs = new HashSet<AlbumVO>();

        for (Album album : albums) {
            AlbumVO albumDTO = new AlbumVO(album);

            Link selfLink = Link.of("http://localhost:8080/api/album/" + album.getId());
            albumDTO.add(selfLink);

            Link artistsLink = Link.of("http://localhost:8080/api/album/" + album.getId() + "/artists")
                    .withRel("artists");
            Link reviewsLink = Link.of("http://localhost:8080/api/album/" + album.getId() + "/reviews")
                    .withRel("reviews");
            albumDTO.add(artistsLink);
            albumDTO.add(reviewsLink);

            albumDTOs.add(albumDTO);
        }

        return ResponseEntity.ok(albumDTOs);
    }

    public ResponseEntity<?> getAlbumById(long id) {
        Album album = albumRepository.findById(id);

        if (album == null) {
            throw new RuntimeException("NOT_FOUND_ALBUM");
        }

        AlbumVO albumDTO = new AlbumVO(album);

        Link selfLink = Link.of("http://localhost:8080/api/album/" + album.getId());
        albumDTO.add(selfLink);

        Link artistsLink = Link.of("http://localhost:8080/api/album/" + album.getId() + "/artists")
                .withRel("artists");
        Link reviewsLink = Link.of("http://localhost:8080/api/album/" + album.getId() + "/reviews")
                .withRel("reviews");
        albumDTO.add(artistsLink);
        albumDTO.add(reviewsLink);

        return ResponseEntity.ok(albumDTO);
    }

    public ResponseEntity<?> getArtistsByAlbumId(long id) {
        Album album = albumRepository.findById(id);

        if (album == null) {
            throw new RuntimeException("NOT_FOUND_ALBUM");
        }

        Set<ArtistDTO> artists = new HashSet<ArtistDTO>();
        album.getArtists().forEach(artistServer -> {
            ArtistDTO artist = new ArtistDTO(artistServer);
            artists.add(artist);
        });

        return ResponseEntity.ok(artists);
    }

    public ResponseEntity<?> getReviewsByAlbumId(long id) {
        Album album = albumRepository.findById(id);

        if (album == null) {
            throw new RuntimeException("NOT_FOUND_ALBUM");
        }

        Set<ReviewVO> reviews = new HashSet<ReviewVO>();
        album.getReviews().forEach(reviewServer -> {
            ReviewVO review = new ReviewVO(reviewServer);
            Link userLink = Link.of("http://localhost:8080/api/user/profile/" + review.getUsername()).withRel("user");
            review.add(userLink);

            reviews.add(review);
        });

        return ResponseEntity.ok(reviews);
    }

}
