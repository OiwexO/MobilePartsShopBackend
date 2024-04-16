package com.nulp.mobilepartsshop.api.v1.review.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.review.dto.response.ReviewResponse;
import com.nulp.mobilepartsshop.core.entity.review.Review;

public class ReviewMapper extends Mapper<Review, ReviewResponse> {

    public ReviewMapper() {}

    @Override
    public ReviewResponse toResponse(Review entity) {
        return ReviewResponse.builder()
                .id(entity.getId())
                .authorId(entity.getAuthor().getId())
                .partId(entity.getPart().getId())
                .rating(entity.getRating())
                .publicationDate(entity.getPublicationDate())
                .title(entity.getTitle())
                .text(entity.getText())
                .build();
    }
}
