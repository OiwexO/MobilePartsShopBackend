package com.nulp.mobilepartsshop.api.v1.review.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.review.dto.request.ReviewRequest;

public class ReviewRequestValidator extends RequestValidator<ReviewRequest> {

    private static final int MIN_RATING = 0;

    private static final int MAX_RATING = 10;

    private static final int MIN_TEXT_LENGTH = 5;

    public ReviewRequestValidator() {}

    @Override
    public boolean isValidRequest(ReviewRequest request) {
        final Long authorId = request.getAuthorId();
        final Long partId = request.getPartId();
        if (!isValidId(authorId) || !isValidId(partId)) {
            return false;
        }
        final Integer rating = request.getRating();
        if (rating == null || rating < MIN_RATING || rating > MAX_RATING) {
            return false;
        }
        final String title = request.getTitle();
        final String text = request.getText();
        return isValidString(title) && isValidString(text) && text.length() >= MIN_TEXT_LENGTH;
    }
}
