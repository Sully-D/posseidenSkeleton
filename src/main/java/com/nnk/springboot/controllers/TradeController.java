package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.util.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for handling Trade-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting Trade.
 */
@Controller
public class TradeController {
    @Autowired
    TradeService tradeService;

    /**
     * Displays a list of all Trade.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of Trade lists
     */
    @RequestMapping("/trade/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails currentUser)
    {
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());
        }
        List<Trade> tradeList = tradeService.findAllTrade();
        model.addAttribute("trades", tradeList);
        return "trade/list";
    }

    /**
     * Displays the form for adding a new Trade list.
     *
     * @param bid the Trade object to bind form data
     * @return the view name for the Trade add form
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    /**
     * Validates and saves a new Trade.
     *
     * @param trade the Trade object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the Trade or add form if there are validation errors
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "/trade/add";
        }
        try {
            tradeService.save(trade);
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/trade/add";
        }

        model.addAttribute("trade", tradeService.findAllTrade());
        return "redirect:/trade/list";
    }

    /**
     * Displays the form for updating an existing Trade list.
     *
     * @param id the ID of the Trade to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the Trade update form or the Trade update view if not found
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = tradeService.getTradeById(id);
            model.addAttribute("trade", trade);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Trade not found with id : " + id);
            return "trade/update";
        }
        return "trade/update";
    }

    /**
     * Validates and updates an existing Trade.
     *
     * @param id the ID of the Trade to update
     * @param trade the RuleName object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the Trade or update form if there are validation errors
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }
        try {
            Utils.intIsValide(id, "ID");

            tradeService.update(trade);

            model.addAttribute("trade", tradeService.findAllTrade());
            return "redirect:/trade/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "trade/update";
        }
    }

    /**
     * Deletes an existing Trade.
     *
     * @param id the ID of the Trade to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the Trade
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.delete(id);
        model.addAttribute("trade", tradeService.findAllTrade());
        return "redirect:/trade/list";
    }
}
