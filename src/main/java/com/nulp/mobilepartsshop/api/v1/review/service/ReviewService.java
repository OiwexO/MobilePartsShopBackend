package com.nulp.mobilepartsshop.api.v1.review.service;

import com.nulp.mobilepartsshop.api.v1.review.dto.request.ReviewRequest;
import com.nulp.mobilepartsshop.core.entity.review.Review;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getAllReviews();

    List<Review> getReviewsByAuthorId(Long authorId);

    List<Review> getReviewsByPartId(Long partId);

    Optional<Review> getReviewById(Long id);

    Review createReview(ReviewRequest reviewRequest) throws EntityNotFoundException;

    Optional<Review> updateReview(Long id, ReviewRequest reviewRequest);

    boolean deleteReview(Long id);
}
