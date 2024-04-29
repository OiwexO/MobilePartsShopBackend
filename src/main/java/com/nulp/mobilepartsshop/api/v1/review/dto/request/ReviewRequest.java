package com.nulp.mobilepartsshop.api.v1.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long authorId;

    private Long partId;

    private Integer rating;

    private String title;

    private String text;
}
