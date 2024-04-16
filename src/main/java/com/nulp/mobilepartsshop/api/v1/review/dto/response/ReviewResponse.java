package com.nulp.mobilepartsshop.api.v1.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;

    private Long authorId;

    private Long partId;

    private Integer rating;

    private Date publicationDate;

    private String title;

    private String text;
}
