package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RatingService.class)
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @MockBean
    private RatingRepository ratingRepository;

    @Test
    public void testFindAll() {
        Rating rating = new Rating();
        rating.setMoodysRating("Test Moody");
        rating.setSandPRating("Test SandPRating");
        rating.setFitchRating("Test FitchRating");
        rating.setOrder(1);
        List<Rating> ratings = Arrays.asList(rating);

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Test Moody");
        rating.setSandPRating("Test SandPRating");
        rating.setFitchRating("Test FitchRating");
        rating.setOrder(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertNotNull(result);
        assertEquals("Test Moody", result.getMoodysRating());
    }

    @Test
    public void testFindById_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.findById(1));
    }

    @Test
    public void testCreateRating() {
        Rating rating = new Rating();
        rating.setMoodysRating("Test Moody");
        rating.setSandPRating("Test SandPRating");
        rating.setFitchRating("Test FitchRating");
        rating.setOrder(1);

        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.createRating(rating);

        assertEquals(rating.getMoodysRating(), result.getMoodysRating());
        assertEquals(rating.getSandPRating(), result.getSandPRating());
        assertEquals(rating.getFitchRating(), result.getFitchRating());
        assertEquals(rating.getOrder(), result.getOrder());
    }

    @Test
    public void testUpdateRating() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Original Moody");
        rating.setSandPRating("Original SandPRating");
        rating.setFitchRating("Original FitchRating");
        rating.setOrder(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating updatedRating = new Rating();
        updatedRating.setMoodysRating("Updated Moody");
        updatedRating.setSandPRating("Updated SandPRating");
        updatedRating.setFitchRating("Updated FitchRating");
        updatedRating.setOrder(2);

        Rating result = ratingService.updateCurvePoint(updatedRating, 1);

        assertEquals("Updated Moody", result.getMoodysRating());
        assertEquals("Updated SandPRating", result.getSandPRating());
        assertEquals("Updated FitchRating", result.getFitchRating());
        assertEquals(2, result.getOrder());
    }

    @Test
    public void testDeleteById() {
        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }

}
