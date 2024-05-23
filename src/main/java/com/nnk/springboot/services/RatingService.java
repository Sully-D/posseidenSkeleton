package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Rating entities.
 * This class provides methods for CRUD operations on Rating entities.
 */
@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    /**
     * Retrieves a Rating by its ID.
     *
     * @param id the ID of the Rating to retrieve.
     * @return an Optional containing the Rating if found, or empty if not found.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public Optional<Rating> getRatingById(Integer id) {
        Utils.intIsValide(id, "id rating");
        return ratingRepository.findById(id);
    }

    /**
     * Retrieves all Ratings.
     *
     * @return a List of all Rating entities.
     */
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * Adds a new Rating.
     *
     * @param rating the Rating entity to add.
     */
    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * Updates an existing Rating by its ID.
     *
     * @param id the ID of the Rating to update.
     * @param updateRating the updated Rating entity.
     * @throws RuntimeException if no Rating is found with the given ID.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public void updateRating(int id, Rating updateRating) {
        Utils.intIsValide(id, "id rating");

        // Retrieve the existing Rating
        Optional<Rating> optionalRating = getRatingById(id);
        if (optionalRating.isEmpty()) {
            throw new RuntimeException("No rating found with this id : " + id);
        }

        Rating rating = optionalRating.get();

        // Update the fields of the existing Rating
        rating.setMoodysRating(updateRating.getMoodysRating());
        rating.setSandPRating(updateRating.getSandPRating());
        rating.setFitchRating(updateRating.getFitchRating());
        rating.setOrderNumber(updateRating.getOrderNumber());

        // Save the updated Rating back to the repository
        ratingRepository.save(rating);
    }

    /**
     * Deletes a Rating by its ID.
     *
     * @param id the ID of the Rating to delete.
     * @throws RuntimeException if no Rating is found with the given ID.
     * @throws IllegalArgumentException if the ID is not valid.
     */
    public void deleteRating(int id) {
        Utils.intIsValide(id, "id rating");

        // Retrieve the existing Rating
        Optional<Rating> optionalRating = getRatingById(id);
        if (optionalRating.isEmpty()) {
            throw new RuntimeException("No rating found with this id : " + id);
        }

        Rating rating = optionalRating.get();

        // Delete the Rating
        ratingRepository.delete(rating);
    }
}
