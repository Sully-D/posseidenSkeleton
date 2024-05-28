package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * Controller for handling RuleName-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting RuleName.
 */
@Controller
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;

    /**
     * Displays a list of all RuleName.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of RuleName lists
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        List<RuleName> ruleNamesList = ruleNameService.findAllRuleName();
        model.addAttribute("ruleName", ruleNamesList);
        return "ruleName/list";
    }

    /**
     * Displays the form for adding a new curve list.
     *
     * @param bid the RuleName object to bind form data
     * @return the view name for the RuleName add form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * Validates and saves a new RuleName.
     *
     * @param ruleName the ruleName object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the RuleName or add form if there are validation errors
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "/ruleName/add";
        }
        try {
            ruleNameService.save(ruleName);
        } catch(IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/ruleName/add";
        }

        model.addAttribute("ruleName", ruleNameService.findAllRuleName());
        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form for updating an existing RuleName list.
     *
     * @param id the ID of the RuleName to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the RuleName update form or the RuleName update view if not found
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = ruleNameService.getRuleNameById(id);
            model.addAttribute("ruleName", ruleName);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "RuleName not found with id : " + id);
            return "ruleName/update";
        }
        return "ruleName/update";
    }

    /**
     * Validates and updates an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param ruleName the RuleName object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the RuleName or update form if there are validation errors
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        try {
            Utils.intIsValide(id, "ID");

            ruleNameService.update(ruleName);

            model.addAttribute("ruleName", ruleNameService.findAllRuleName());
            return "redirect:/ruleName/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "ruleName/update";
        }
    }

    /**
     * Deletes an existing RuleName.
     *
     * @param id the ID of the RuleName to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the RuleName
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        model.addAttribute("ruleName", ruleNameService.findAllRuleName());
        return "redirect:/ruleName/list";
    }
}
