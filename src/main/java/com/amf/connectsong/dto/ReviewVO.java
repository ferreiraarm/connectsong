package com.amf.connectsong.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.amf.connectsong.model.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewVO extends RepresentationModel<ReviewVO> implements Serializable {
    private int id;
    private int grade;
    private String comment;
    private String username;
    private String userFullname;
    private String albumName;

    public ReviewVO(Review review) {
        this.id = review.getId();
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.username = review.getUser().getUsername();
        this.userFullname = review.getUser().getName();
        this.albumName = review.getAlbum().getName();
    }
}
