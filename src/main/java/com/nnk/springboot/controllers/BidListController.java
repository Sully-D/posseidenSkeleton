package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for handling BidList-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting bid lists.
 */
@Controller
public class BidListController {

    private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    /**
     * Displays a list of all bid lists.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of bid lists
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());
        }

        List<BidList> bidLists = bidListService.findAllBids();
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    /**
     * Displays the form for adding a new bid list.
     *
     * @param bidLists the BidList object to bind form data
     * @return the view name for the bid list add form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidLists) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new bid list.
     *
     * @param bidLists the BidList object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the bid list list or add form if there are validation errors
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidList bidLists, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "bidList/add";
        }
        try {
            Utils.stringIsValide(bidLists.getAccount(), "Account");
            Utils.stringIsValide(bidLists.getType(), "Type");
            bidListService.save(bidLists);
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "bidList/add";
        }

        return "redirect:/bidList/list";
    }

    /**
     * Displays the form for updating an existing bid list.
     *
     * @param id the ID of the bid list to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the bid list update form or the bid list update view if not found
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            BidList bidList = bidListService.getBidById(id);
            model.addAttribute("bidList", bidList);
        } catch (RuntimeException e) {
            logger.error("Bid not found with id: " + id, e);
            model.addAttribute("errorMessage", "Bid not found with id: " + id);
            return "redirect:/bidList/list";
        }
        return "bidList/update";
    }

    /**
     * Validates and updates an existing bid list.
     *
     * @param id the ID of the bid list to update
     * @param bidList the BidList object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the bid list list or update form if there are validation errors
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        try {
            Utils.stringIsValide(bidList.getAccount(), "Account");
            Utils.stringIsValide(bidList.getType(), "Type");

            bidList.setBidListId(id);
            bidListService.updateBid(bidList);

            return "redirect:/bidList/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
    }

    /**
     * Deletes an existing bid list.
     *
     * @param id the ID of the bid list to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the bid list list
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidListService.delete(id);
        } catch (RuntimeException e) {
            logger.error("Error deleting bid with id: " + id, e);
            model.addAttribute("errorMessage", "Error deleting bid with id: " + id);
        }
        return "redirect:/bidList/list";
    }
}
