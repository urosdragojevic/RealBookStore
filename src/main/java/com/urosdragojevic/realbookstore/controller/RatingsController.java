package com.urosdragojevic.realbookstore.controller;

import com.urosdragojevic.realbookstore.domain.Rating;
import com.urosdragojevic.realbookstore.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RatingsController {
    private static final Logger LOG = LoggerFactory.getLogger(RatingsController.class);

    private RatingRepository ratingRepository;

    public RatingsController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping(value = "/ratings")
    public String createOrUpdateRating(@ModelAttribute Rating rating) {
        rating.setUserId(1);
        ratingRepository.createOrUpdate(rating);

        return "redirect:/books/" + rating.getBookId();
    }
}
