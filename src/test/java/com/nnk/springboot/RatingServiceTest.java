package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void findAllRating_Successful() {
        // Arrange
        Rating rating1 = new Rating();
        rating1.setId(1);
        rating1.setMoodysRating("MoodysRating1");
        rating1.setSandPRating("SandPRating1");
        rating1.setFitchRating("FitchRating1");
        rating1.setOrderNumber(1);

        Rating rating2 = new Rating();
        rating2.setId(2);
        rating2.setMoodysRating("MoodysRating2");
        rating2.setSandPRating("SandPRating2");
        rating2.setFitchRating("FitchRating2");
        rating2.setOrderNumber(2);

        List<Rating> listRatings = Arrays.asList(rating1, rating2);

        when(ratingRepository.findAll()).thenReturn(listRatings);

        // Act
        List<Rating> result = ratingService.getAllRating();

        //Assert
        assertEquals(2, result.size());
        assertEquals("MoodysRating1", result.get(0).getMoodysRating());
        assertEquals("MoodysRating2", result.get(1).getMoodysRating());
    }

    @Test
    public void addRating_Successful() {
        // Arrange
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("MoodysRating1");
        rating.setSandPRating("SandPRating1");
        rating.setFitchRating("FitchRating1");
        rating.setOrderNumber(1);

        // Act
        ratingService.addRating(rating);

        // Assert
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void getRatingById() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("MoodysRating1");
        rating.setSandPRating("SandPRating1");
        rating.setFitchRating("FitchRating1");
        rating.setOrderNumber(1);

        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.getRatingById(1);

        assertTrue(result.isPresent());
        assertEquals("MoodysRating1", result.get().getMoodysRating());
    }
}
