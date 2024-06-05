package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

/**
 * Controller for handling Rating-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting ratings.
 */
@Controller
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    RatingRepository ratingRepository;

    /**
     * Displays a list of all ratings.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of ratings
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        List<Rating> listRatings = ratingService.getAllRating();
        model.addAttribute("ratings", listRatings);
        return "/rating/list";
    }

    /**
     * Displays the form for adding a new rating.
     *
     * @param rating the Rating object to bind form data
     * @return the view name for the rating add form
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates and saves a new rating.
     *
     * @param rating the Rating object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the rating list or add form if there are validation errors
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.addRating(rating);
        return "redirect:rating/list";
    }

    /**
     * Displays the form for updating an existing rating.
     *
     * @param id the ID of the rating to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the rating update form or the rating list if not found
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.getRatingById(id);

        if (rating.isEmpty()) {
            model.addAttribute("error", "Rating not found");
            return "redirect:/rating/list";
        }

        model.addAttribute("rating", rating.get());
        return "rating/update";
    }

    /**
     * Validates and updates an existing rating.
     *
     * @param id the ID of the rating to update
     * @param rating the Rating object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the rating list or update form if there are validation errors
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rating", rating);
            return "rating/update";
        }

        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Deletes an existing rating.
     *
     * @param id the ID of the rating to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the rating list
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
