package com.amf.connectsong.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.AddressDTO;
import com.amf.connectsong.dto.CreateReviewDTO;
import com.amf.connectsong.dto.ProfileDTO;
import com.amf.connectsong.dto.ReviewDTO;
import com.amf.connectsong.dto.UpdateProfileDTO;
import com.amf.connectsong.model.Album;
import com.amf.connectsong.model.Review;
import com.amf.connectsong.model.User;
import com.amf.connectsong.model.UserAddress;
import com.amf.connectsong.repository.AlbumRepository;
import com.amf.connectsong.repository.ReviewRepository;
import com.amf.connectsong.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public void addUser(User newUser) {
        userRepository.findByUsername(newUser.getUsername());
        userRepository.save(newUser);
    }

    public ResponseEntity<?> getProfile(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        ProfileDTO profileDTO = new ProfileDTO(user.get().getName(), user.get().getUsername(), user.get().getEmail());

        return ResponseEntity.ok(profileDTO);
    }

    public ResponseEntity<?> updateProfile(UpdateProfileDTO profile, String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        user.get().setName(profile.getName());
        user.get().setEmail(profile.getEmail());

        userRepository.save(user.get());

        return ResponseEntity.ok("Profile updated successfully!");
    }

    public ResponseEntity<?> addAddress(AddressDTO address, String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        UserAddress userAddress = user.get().getUserAddress();

        if (userAddress == null) {
            userAddress = new UserAddress();
        }

        userAddress.setCity(address.getCity());
        userAddress.setState(address.getState());
        userAddress.setPostalCode(address.getPostalCode());
        userAddress.setCountry(address.getCountry());
        userAddress.setNumber(Integer.parseInt(address.getNumber()));
        userAddress.setAddress(address.getAddress());
        userAddress.setUser(user.get());

        user.get().setUserAddress(userAddress);
        userRepository.save(user.get());

        return ResponseEntity.ok("Address added successfully!");
    }

    public ResponseEntity<?> getUserAddress(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        UserAddress userAddress = user.get().getUserAddress();

        if (userAddress == null) {
            throw new RuntimeException("USER_ADDRESS_NOT_FOUND");
        }

        AddressDTO addressDTO = new AddressDTO(
                userAddress.getCity(),
                userAddress.getState(),
                userAddress.getPostalCode(),
                userAddress.getCountry(),
                String.valueOf(userAddress.getNumber()),
                userAddress.getAddress());

        return ResponseEntity.ok(addressDTO);
    }

    public ResponseEntity<?> addReview(CreateReviewDTO reviewDTO, String token, long album_id) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> currentUser = userRepository.findByUsername(username);

        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Album currentAlbum = albumRepository.findById(album_id);

        if (currentAlbum == null) {
            throw new RuntimeException("ALBUM_NOT_FOUND");
        }

        Review review = reviewRepository.findByUserAndAlbum(currentUser.get(), currentAlbum);

        if (review == null) {
            review = new Review();
        }

        review.setAlbum(currentAlbum);
        review.setUser(currentUser.get());
        review.setComment(reviewDTO.getComment());
        review.setGrade(reviewDTO.getGrade());

        reviewRepository.save(review);

        return ResponseEntity.ok("Review added successfully!");
    }

    public ResponseEntity<?> getReviews(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> currentUser = userRepository.findByUsername(username);

        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Review[] reviews = currentUser.get().getReviews().toArray(Review[]::new);

        ReviewDTO[] returnReviews = new ReviewDTO[reviews.length];

        for (int i = 0; i < reviews.length; i++) {
            ReviewDTO reviewDTO = new ReviewDTO(reviews[i]);
            Link userLink = Link.of("http://localhost:8080/api/user/profile/" + reviews[i].getUser().getUsername())
                    .withRel("user");
            reviewDTO.add(userLink);

            returnReviews[i] = reviewDTO;
        }

        return ResponseEntity.ok(returnReviews);
    }

}
