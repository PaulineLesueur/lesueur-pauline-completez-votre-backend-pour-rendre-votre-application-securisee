package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Retrieves all Rating entries.
     *
     * @return a list of all Rating objects present in the database.
     */
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a Rating object by its ID.
     *
     * @param id the ID of the Rating object to retrieve.
     * @return the Rating object corresponding to the provided ID.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public Rating findById(int id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * Creates a new Rating object and saves it to the database.
     *
     * @param rating the Rating object to create.
     * @return the created and saved Rating object.
     */
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Updates an existing Rating object with the provided new values.
     *
     * @param rating the Rating object containing the new values.
     * @param id the ID of the Rating object to update.
     * @return the updated and saved Rating object.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public Rating updateCurvePoint(Rating rating, int id) {
        Rating updatedRating = findById(id);
        updatedRating.setMoodysRating(rating.getMoodysRating());
        updatedRating.setSandPRating(rating.getSandPRating());
        updatedRating.setFitchRating(rating.getFitchRating());
        updatedRating.setOrder(rating.getOrder());
        return ratingRepository.save(updatedRating);
    }

    /**
     * Deletes a Rating object by its ID.
     *
     * @param id the ID of the Rating object to delete.
     */
    public void deleteById(int id) {
        ratingRepository.deleteById(id);
    }
}
