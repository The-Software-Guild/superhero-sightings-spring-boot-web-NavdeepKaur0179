/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.service.ClassDataValidationException;
import com.navdeep.superheroSightings.service.ClassEmptyListException;
import com.navdeep.superheroSightings.service.SuperHeroServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nkaur
 */
@Controller
public class LocationController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    public LocationController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    public static String exceptionErrorMessage = "";
    Location location;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    @GetMapping("locations")
    public String getLocations(Model model) {
        try {
            List<Location> locations = superHeroServiceLayer.getAllLocations();
//            location = new Location();
//            location.setAddress("");
//            location.setDescription("");
//            location.setName("");
//            location.setLatitude("");
//            location.setLongitude("");
//            model.addAttribute("location", location);
            model.addAttribute("locations", locations);
            return "locations";
        } catch (ClassEmptyListException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }

    }

    @PostMapping("/addLocation")
    public String addLocation(Location location, Model model) {
        violations = validate.validate(location);
        model.addAttribute("errors", violations);
        if (!violations.isEmpty()) {
            return "errorPage";
        }
        try {
            superHeroServiceLayer.addLocation(location);
            return "redirect:/locations";
        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "errorPage";
        }
    }

    @PostMapping("editLocation")
    public String editLocation(Location location, HttpServletRequest request, Model model) {
        violations = validate.validate(location);
        model.addAttribute("errors", violations);
        if (!violations.isEmpty()) {
            return "errorPage";
        }
        try {
            superHeroServiceLayer.updateLocation(location);
            return "redirect:/locations";

        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        superHeroServiceLayer.deleteLocationById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/locations";
    }

    @GetMapping("/errorPage")
    public String errorPage(Model model) {
        model.addAttribute("exceptionErrorMessage", exceptionErrorMessage);
        return "errorpage";
    }
}
