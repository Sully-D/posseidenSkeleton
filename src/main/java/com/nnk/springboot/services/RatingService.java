package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public Optional<Rating> getRatingById(Integer id) {
        Utils.validNumber(id, "id rating");
        return ratingRepository.findById(id);
    }

    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public void updateRating(int id, Rating updateRating) {
        Utils.validNumber(id, "id rating");

        Optional<Rating> optionalRating = getRatingById(id);
        if (optionalRating.isEmpty()) {
            throw new RuntimeException("No rating found with this id : " + id);
        }

        Rating rating = optionalRating.get();

        rating.setMoodysRating(updateRating.getMoodysRating());
        rating.setSandPRating(updateRating.getSandPRating());
        rating.setFitchRating(updateRating.getFitchRating());
        rating.setOrderNumber(updateRating.getOrderNumber());

        ratingRepository.save(rating);
    }

    public void deleteRating(int id) {
        Utils.validNumber(id, "id rating");

        Optional<Rating> optionalRating = getRatingById(id);
        if (optionalRating.isEmpty()) {
            throw new RuntimeException("No rating found with this id : " + id);
        }

        Rating rating = optionalRating.get();

        ratingRepository.delete(rating);
    }
}
