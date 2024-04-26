package com.nulp.mobilepartsshop.core.service.review;

import com.nulp.mobilepartsshop.api.v1.review.dto.request.ReviewRequest;
import com.nulp.mobilepartsshop.api.v1.review.service.ReviewService;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.review.Review;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.part.PartRepository;
import com.nulp.mobilepartsshop.core.repository.review.ReviewRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final PartRepository partRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByAuthorId(Long authorId) {
        return reviewRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Review> getReviewsByPartId(Long partId) {
        return reviewRepository.findByPartId(partId);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review createReview(ReviewRequest reviewRequest) throws EntityNotFoundException {
        final User author = userRepository.findById(reviewRequest.getAuthorId()).orElseThrow(EntityNotFoundException::new);
        final Part part = partRepository.findById(reviewRequest.getPartId()).orElseThrow(EntityNotFoundException::new);
        final Review review = Review.builder()
                .author(author)
                .part(part)
                .rating(reviewRequest.getRating())
                .publicationDate(reviewRequest.getPublicationDate())
                .title(reviewRequest.getTitle())
                .text(reviewRequest.getText())
                .isEdited(false)
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> updateReview(Long id, ReviewRequest request) {
        final Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            return Optional.empty();
        }
        final Review review = optionalReview.get();
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setText(request.getText());
        review.setIsEdited(true);
        return Optional.of(reviewRepository.save(review));
    }

    @Override
    public boolean deleteReview(Long id) {
        final Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            return false;
        }
        reviewRepository.deleteById(id);
        return true;
    }
}
