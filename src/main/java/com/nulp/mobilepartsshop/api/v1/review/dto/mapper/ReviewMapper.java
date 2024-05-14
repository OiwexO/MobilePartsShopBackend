package com.nulp.mobilepartsshop.api.v1.review.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.review.dto.response.ReviewResponse;
import com.nulp.mobilepartsshop.api.v1.user.dto.mapper.UserMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import com.nulp.mobilepartsshop.core.entity.review.Review;

public class ReviewMapper extends Mapper<Review, ReviewResponse> {

    private final UserMapper userMapper = new UserMapper();

    public ReviewMapper() {}

    @Override
    public ReviewResponse toResponse(Review entity) {
        UserResponse userResponse = userMapper.toResponse(entity.getAuthor());
        return ReviewResponse.builder()
                .id(entity.getId())
                .author(userResponse)
                .partId(entity.getPart().getId())
                .rating(entity.getRating())
                .publicationDate(entity.getPublicationDate())
                .title(entity.getTitle())
                .text(entity.getText())
                .isEdited(entity.getIsEdited())
                .build();
    }
}
