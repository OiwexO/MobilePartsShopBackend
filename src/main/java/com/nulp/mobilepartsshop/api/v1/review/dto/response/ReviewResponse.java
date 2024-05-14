package com.nulp.mobilepartsshop.api.v1.review.dto.response;

import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;

    private UserResponse author;

    private Long partId;

    private Integer rating;

    private Date publicationDate;

    private String title;

    private String text;

    private boolean isEdited;
}
