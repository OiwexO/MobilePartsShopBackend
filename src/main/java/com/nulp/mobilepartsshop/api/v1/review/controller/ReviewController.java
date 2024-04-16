package com.nulp.mobilepartsshop.api.v1.review.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.review.dto.mapper.ReviewMapper;
import com.nulp.mobilepartsshop.api.v1.review.dto.request.ReviewRequest;
import com.nulp.mobilepartsshop.api.v1.review.dto.response.ReviewResponse;
import com.nulp.mobilepartsshop.api.v1.review.dto.validator.ReviewRequestValidator;
import com.nulp.mobilepartsshop.api.v1.review.service.ReviewService;
import com.nulp.mobilepartsshop.core.entity.review.Review;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReviewController.MAPPING)
@RequiredArgsConstructor
public class ReviewController {

    public static final String MAPPING = ApiConstants.REVIEW_MAPPING_V1 + "/reviews";

    private final ReviewService reviewService;

    private final ReviewRequestValidator requestValidator = new ReviewRequestValidator();

    private final ReviewMapper mapper = new ReviewMapper();

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewResponse> reviewResponses = mapper.toResponseList(reviews);
        return ResponseEntity.ok(reviewResponses);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForUser(@PathVariable Long authorId) {
        if (!requestValidator.isValidId(authorId)) {
            return ResponseEntity.badRequest().build();
        }
        List<Review> reviews = reviewService.getReviewsByAuthorId(authorId);
        List<ReviewResponse> reviewResponses = mapper.toResponseList(reviews);
        return ResponseEntity.ok(reviewResponses);
    }

    @GetMapping("/part/{partId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForPart(@PathVariable Long partId) {
        if (!requestValidator.isValidId(partId)) {
            return ResponseEntity.badRequest().build();
        }
        List<Review> reviews = reviewService.getReviewsByPartId(partId);
        List<ReviewResponse> reviewResponses = mapper.toResponseList(reviews);
        return ResponseEntity.ok(reviewResponses);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        if (!requestValidator.isValidId(reviewId)) {
            return ResponseEntity.badRequest().build();
        }
        return reviewService.getReviewById(reviewId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        if (!requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Review review = reviewService.createReview(request);
            final ReviewResponse response = mapper.toResponse(review);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // invalid id passed as one of the fields
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest request
    ) {
        if (!requestValidator.isValidId(reviewId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        return reviewService.updateReview(reviewId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        if (!requestValidator.isValidId(reviewId)) {
            return ResponseEntity.badRequest().build();
        }
        if (reviewService.deleteReview(reviewId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
