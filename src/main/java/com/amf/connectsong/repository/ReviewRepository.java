package com.amf.connectsong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amf.connectsong.model.Album;
import com.amf.connectsong.model.Review;
import com.amf.connectsong.model.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndAlbum(User user, Album album);
}
