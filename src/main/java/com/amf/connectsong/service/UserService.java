package com.amf.connectsong.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amf.connectsong.config.jwt.JwtUtils;
import com.amf.connectsong.dto.AddressDTO;
import com.amf.connectsong.dto.ReviewDTO;
import com.amf.connectsong.dto.ProfileVO;
import com.amf.connectsong.dto.ReviewVO;
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

        ProfileVO profileDTO = new ProfileVO(user.get().getName(), user.get().getUsername(), user.get().getEmail());

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

    public ResponseEntity<?> addReview(ReviewDTO reviewDTO, String token, long album_id) {
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

        ReviewVO[] returnReviews = new ReviewVO[reviews.length];

        for (int i = 0; i < reviews.length; i++) {
            ReviewVO reviewDTO = new ReviewVO(reviews[i]);
            Link userLink = Link.of("http://localhost:8080/api/user/profile/" + reviews[i].getUser().getUsername())
                    .withRel("user");
            reviewDTO.add(userLink);

            returnReviews[i] = reviewDTO;
        }

        return ResponseEntity.ok(returnReviews);
    }

    public String addProfilePicture(String token, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (filename.contains("..")) {
            throw new RuntimeException("FILENAME_IS_INVALID");
        }

        String fileExtension = getLastCharactersUntilDot(filename);
        filename = "profile." + fileExtension;

        Logger logger = LoggerFactory.getLogger(UserService.class);
        logger.info("File extension: " + fileExtension);
        logger.info("Filename: " + filename);

        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> currentUser = userRepository.findByUsername(username);

        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        if (!currentUser.get().getPicturePath().isEmpty()) {
            File oldFile = new File(currentUser.get().getPicturePath());
            oldFile.delete();
            logger.info("Old file deleted!");
        }

        File directory = new File("src/main/resources/static/images/profiles/" + currentUser.get().getUsername());

        if (!directory.exists()) {
            directory.mkdir();
        }

        Path rootLocation = Paths.get("src/main/resources/static/images/profiles/" + currentUser.get().getUsername());
        Path destinationFile = rootLocation.resolve(filename);

        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("CANNOT_SAVE_FILE");
        }

        currentUser.get().setPicturePath(destinationFile.toString());
        User savedUser = userRepository.save(currentUser.get());

        logger.info("File saved!");
        logger.info(savedUser.getPicturePath());

        return filename;
    }

    public String getLastCharactersUntilDot(String input) {
        int dotIndex = input.lastIndexOf('.');
        if (dotIndex != -1) {
            return input.substring(dotIndex + 1, input.length());
        } else {
            return input;
        }
    }

    public Resource getProfilePicture(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        Optional<User> currentUser = userRepository.findByUsername(username);

        if (!currentUser.isPresent()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Path rootLocation = Paths.get("src/main/resources/static/images/profiles/" + currentUser.get().getUsername());
        String filename = "profile." + getLastCharactersUntilDot(currentUser.get().getPicturePath());
        Path filePath = rootLocation.resolve(filename).normalize();

        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("CANNOT_READ_FILE");
        }
    }

}
