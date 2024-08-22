package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(int id) { return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id)); }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating updateCurvePoint(Rating rating, int id) {
        Rating updatedRating = findById(id);
        updatedRating.setMoodysRating(rating.getMoodysRating());
        updatedRating.setSandPRating(rating.getSandPRating());
        updatedRating.setFitchRating(rating.getFitchRating());
        updatedRating.setOrder(rating.getOrder());
        return ratingRepository.save(updatedRating);
    }

    public void deleteById(int id) {
        ratingRepository.deleteById(id);
    }
}
