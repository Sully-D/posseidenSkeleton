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


@Controller
public class BidListController {
    // TODO: Inject Bid service - Done
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view - Done
        List<BidList> bidLists = bidListService.findAllBids();
        model.addAttribute("bids", bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list - Done
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

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        try {
            BidList bidList = bidListService.getBidById(id);
            model.addAttribute("bidList", bidList);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Bid not found with id : " + id);
            return "bidList/update";
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
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

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.delete(id);
        model.addAttribute("bidList", bidListService.findAllBids());
        return "redirect:/bidList/list";
    }
}
