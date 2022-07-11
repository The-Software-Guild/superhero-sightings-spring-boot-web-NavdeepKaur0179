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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    String exceptionErrorMessage = "";

    @GetMapping("locations")
    public String getLocations(Model model) {
        try {
            List<Location> locations = superHeroServiceLayer.getAllLocations();
            model.addAttribute("locations", locations);
            return "locations";
        } catch (ClassEmptyListException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }

    }

    @PostMapping("/addLocation")
    public String addLocation(Location location) {
        try {
            superHeroServiceLayer.addLocation(location);
            return "redirect:/locations";
        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
    }

    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        String locationName = request.getParameter("name");
        String locationDescription = request.getParameter("description");
        String locationAddress = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        String locationId = request.getParameter("id");

        Location location = new Location();
        location.setId(Integer.parseInt(locationId));
        location.setName(locationName);
        location.setDescription(locationDescription);
        location.setAddress(locationAddress);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
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
