package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
import com.nnk.springboot.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
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
 * Controller for handling CurvePoint-related web requests.
 * This class provides methods for displaying, adding, updating, and deleting CurvePoint.
 */
@Controller
public class CurveController {
    @Autowired
    CurveService curveService;

    /**
     * Displays a list of all curve point.
     *
     * @param model the model to add attributes used for rendering view
     * @return the view name for displaying the list of curve point lists
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails currentUser)
    {
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());
        }
        List<CurvePoint> curvePointList = curveService.findAllCurves();
        model.addAttribute("curvePoints", curvePointList);
        return "curvePoint/list";
    }

    /**
     * Displays the form for adding a new curve list.
     *
     * @param bid the CurvePoint object to bind form data
     * @return the view name for the CurvePoint add form
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new bid list.
     *
     * @param curvePoint the curvePoint object to validate and save
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the curve point list or add form if there are validation errors
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        curveService.save(curvePoint);
        return "redirect:list";
    }

    /**
     * Displays the form for updating an existing curve list.
     *
     * @param id the ID of the bid list to update
     * @param model the model to add attributes used for rendering view
     * @return the view name for the curve list update form or the curve list update view if not found
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = curveService.getCurveById(id);
            model.addAttribute("curvePoint", curvePoint);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Curve not found with id : " + id);
            return "curvePoint/update";
        }
        return "curvePoint/update";
    }

    /**
     * Validates and updates an existing bid list.
     *
     * @param id the ID of the curve list to update
     * @param curvePoint the BidList object to validate and update
     * @param result the BindingResult object to hold validation errors
     * @param model the model to add attributes used for rendering view
     * @return the view name for the curve list or update form if there are validation errors
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }
        try {
            Utils.intIsValide(id, "ID");

            curveService.update(curvePoint);

            model.addAttribute("curvePoint", curveService.findAllCurves());
            return "redirect:/curvePoint/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }
    }

    /**
     * Deletes an existing bid list.
     *
     * @param id the ID of the curve list to delete
     * @param model the model to add attributes used for rendering view
     * @return the view name for the curve list
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curveService.delete(id);
        model.addAttribute("curvePoint", curveService.findAllCurves());
        return "redirect:/curvePoint/list";
    }
}
