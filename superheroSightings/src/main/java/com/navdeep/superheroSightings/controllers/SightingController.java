/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Sighting;
import com.navdeep.superheroSightings.service.ClassDataValidationException;
import com.navdeep.superheroSightings.service.ClassEmptyListException;
import com.navdeep.superheroSightings.service.ClassNoSuchRecordException;
import com.navdeep.superheroSightings.service.SuperHeroServiceLayer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class SightingController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    List<String> customErrors;
    Error error = new Error();

    public SightingController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    List<Hero> heroes;
    List<Sighting> sightings;
    List<Location> locations;

    @GetMapping("sightings")
    public String getSightings(Model model) {
        try {
            heroes = superHeroServiceLayer.getAllHeros();
            sightings = superHeroServiceLayer.getAllSightings();
            locations = superHeroServiceLayer.getAllLocations();
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        model.addAttribute("heroes", heroes);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("/addSighting")
    public String addSighting(HttpServletRequest request, Model model) {
        customErrors = new ArrayList<>();
        Hero hero = null;
        Location location = null;
        String locationId = request.getParameter("locationId");
        System.out.println(locationId);
        //adding error message for missing location
        if (locationId != null) {
            try {
                location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
            } catch (ClassNoSuchRecordException e) {
                LocationController.exceptionErrorMessage = e.getMessage();
                customErrors.add(("You must select atleast one Location"));
                model.addAttribute("customErrors", customErrors);
                return "redirect:/errorPage";
            }
        } else {
            customErrors.add(("You must select atleast one Location"));
            model.addAttribute("customErrors", customErrors);
        }

        //adding error message for missing hero
        String heroId = request.getParameter("heroId");
        if (heroId != null) {
            try {
                hero = superHeroServiceLayer.getHeroById(Integer.parseInt(heroId));
            } catch (ClassNoSuchRecordException e) {
                LocationController.exceptionErrorMessage = e.getMessage();
                customErrors.add(("You must select atleast one hero for sighting record."));
                model.addAttribute("customErrors", customErrors);
                return "redirect:/errorPage";
            }
        } else {
            customErrors.add(("You must select atleast one hero for sighting record."));
            model.addAttribute("customErrors", customErrors);
        }

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);

        //adding error message for missing date
        if (!request.getParameter("date").equals("")) {
            //adding error message for future/Invalid date selected
            if (!LocalDateTime.parse(request.getParameter("date")).isAfter(LocalDateTime.now())) {
                sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
            } else {
                customErrors.add("You cannot select future date for sighting record.");
                model.addAttribute("customErrors", customErrors);
            }
            sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        } else {
            customErrors.add("You must select date of sighting.");
            model.addAttribute("customErrors", customErrors);
        }

        sighting.setDescription(request.getParameter("description"));

        //check violations else add
        violations = validate.validate(sighting);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("customErrors", customErrors);
            return "errorPage";
        } else {
            error.setMessage("Errors are as follows:");
            model.addAttribute("errors", error);
            model.addAttribute("customErrors", customErrors);
            if (customErrors.isEmpty()) {
                try {
                    superHeroServiceLayer.addSighting(sighting);
                } catch (ClassDataValidationException e) {
                    LocationController.exceptionErrorMessage = e.getMessage() + "\n" + "Please go back from browser's back button to return to correct the form entries";
                    return "redirect:/errorPage";
                }
                return "redirect:/sightings";
            } else {
                return "errorPage";
            }
        }
    }

    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        customErrors = new ArrayList<>();
        String locationId = request.getParameter("locationId");
        Location location = null;
        Hero hero = null;
//        try {
//            location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
//            String heroId = request.getParameter("heroId");
//            hero = superHeroServiceLayer.getHeroById(Integer.parseInt(heroId));
//        } catch (ClassNoSuchRecordException e) {
//            LocationController.exceptionErrorMessage = e.getMessage();
//            return "redirect:/errorPage";
//        }

        if (locationId != null) {
            try {
                location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
            } catch (ClassNoSuchRecordException e) {
                LocationController.exceptionErrorMessage = e.getMessage();
                customErrors.add(("You must select atleast one Location"));
                model.addAttribute("customErrors", customErrors);
                return "redirect:/errorPage";
            }
        } else {
            customErrors.add(("You must select atleast one Location"));
            model.addAttribute("customErrors", customErrors);
        }

        //adding error message for missing hero
        String heroId = request.getParameter("heroId");
        if (heroId != null) {
            try {
                hero = superHeroServiceLayer.getHeroById(Integer.parseInt(heroId));
            } catch (ClassNoSuchRecordException e) {
                LocationController.exceptionErrorMessage = e.getMessage();
                customErrors.add(("You must select atleast one hero for sighting record."));
                model.addAttribute("customErrors", customErrors);
                return "redirect:/errorPage";
            }
        } else {
            customErrors.add(("You must select atleast one hero for sighting record."));
            model.addAttribute("customErrors", customErrors);
        }

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDescription(request.getParameter("description"));
        sighting.setId(Integer.parseInt(request.getParameter("id")));
        LocalDateTime dateTime;

        //adding error message for missing date
        if (!request.getParameter("date").equals("")) {
            //adding error message for future/Invalid date selected
            if (!LocalDateTime.parse(request.getParameter("date")).isAfter(LocalDateTime.now())) {
                dateTime=LocalDateTime.parse(request.getParameter("date"));
                sighting.setDate(dateTime);
            } else {
                customErrors.add("You cannot select future date for sighting record.");
                model.addAttribute("customErrors", customErrors);
            }
            sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        } else {
            customErrors.add("You must select date of sighting.");
            model.addAttribute("customErrors", customErrors);
        }

        //check violations else add
        violations = validate.validate(sighting);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("customErrors", customErrors);
            return "errorPage";
        } else {
            error.setMessage("Errors are as follows:");
            model.addAttribute("errors", error);
            model.addAttribute("customErrors", customErrors);
            if (customErrors.isEmpty()) {
                try {
                    superHeroServiceLayer.updateSighting(sighting);
                } catch (ClassDataValidationException e) {
                    LocationController.exceptionErrorMessage = e.getMessage() + "\n" + "Please go back from browser's back button to return to correct the form entries";
                    return "redirect:/errorPage";
                }
                return "redirect:/sightings";
            } else {
                return "errorPage";
            }
        }  
    }

    @PostMapping("/deleteSighting")
    public String deleteSighting(HttpServletRequest request
    ) {
        superHeroServiceLayer.deleteSightingById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/sightings";
    }

    @PostMapping("sightingsByDate")
    public String sightingsByDate(HttpServletRequest request, Model model
    ) {
        LocalDate sightingDate = LocalDate.parse(request.getParameter("datetime"));
        //LocalDate sightingDate = LocalDate.parse(dateTime);
        LocalDateTime sightingDateTime = sightingDate.atStartOfDay();
        sightings = superHeroServiceLayer.getAllSightingByDate(sightingDateTime);
        model.addAttribute("sightings", sightings);
        try {
            heroes = superHeroServiceLayer.getAllHeros();
            locations = superHeroServiceLayer.getAllLocations();
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("sightingsByLocation")
    public String sightingsByLocation(HttpServletRequest request, Model model
    ) {
        String locationId = request.getParameter("locationId");
        Location location;
        try {
            location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
        } catch (ClassNoSuchRecordException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        sightings = superHeroServiceLayer.getAllSightingByLocation(location);
        model.addAttribute("sightings", sightings);
        try {
            heroes = superHeroServiceLayer.getAllHeros();
            locations = superHeroServiceLayer.getAllLocations();
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

}
