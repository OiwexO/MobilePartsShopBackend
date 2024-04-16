package com.nulp.mobilepartsshop.core.repository.review;

import com.nulp.mobilepartsshop.core.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAuthorId(Long authorId);

    List<Review> findByPartId(Long partId);
}
