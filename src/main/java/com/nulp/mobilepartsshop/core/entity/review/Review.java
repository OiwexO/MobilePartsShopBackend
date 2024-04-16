package com.nulp.mobilepartsshop.core.entity.review;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    private Integer rating;

    private Date publicationDate;

    private String title;

    private String text;

    private Boolean isEdited;
}
