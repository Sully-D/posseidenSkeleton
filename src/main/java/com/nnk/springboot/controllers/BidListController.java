package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.util.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for handling BidList-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting bid lists.
 */
@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    /**
     * Displays a list of all bid lists.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of bid lists
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        List<BidList> bidLists = bidListService.findAllBids();
        model.addAttribute("bids", bidLists);
        return "bidList/list";
    }

    /**
     * Displays the form for adding a new bid list.
     *
     * @param bid the BidList object to bind form data
     * @return the view name for the bid list add form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new bid list.
     *
     * @param bid the BidList object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the bid list list or add form if there are validation errors
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "bidList/add";
        }
        try {
            Utils.stringIsValide(bid.getAccount(), "Account");
            Utils.stringIsValide(bid.getType(), "Type");
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "bidList/add";
        }

        bidListService.save(bid);

        model.addAttribute("bidList", bidListService.findAllBids());
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
            model.addAttribute("errorMessage", "Bid not found with id : " + id);
            return "bidList/update";
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

            model.addAttribute("bidList", bidListService.findAllBids());
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
        bidListService.delete(id);
        model.addAttribute("bidList", bidListService.findAllBids());
        return "redirect:/bidList/list";
    }
}
