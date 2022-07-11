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
import java.time.LocalDateTime;
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
public class SightingController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    public SightingController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    String exceptionErrorMessage = "";

    @GetMapping("sightings")
    public String getSightings(Model model) {
        List<Hero> heroes;
         List<Sighting> sightings;
         List<Location> locations;
        try {
            heroes = superHeroServiceLayer.getAllHeros();
            sightings = superHeroServiceLayer.getAllSightings();
            locations = superHeroServiceLayer.getAllLocations();
        } catch (ClassEmptyListException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        model.addAttribute("heroes", heroes);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("/addSighting")
    public String addSighting(HttpServletRequest request) {
        Hero hero;
        Location location;
        try {

            String locationId = request.getParameter("locationId");
            location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
            String heroId = request.getParameter("heroId");
            hero = superHeroServiceLayer.getHeroById(Integer.parseInt(heroId));
        } catch (ClassNoSuchRecordException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        sighting.setDescription(request.getParameter("description"));
        try {
            superHeroServiceLayer.addSighting(sighting);
        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        return "redirect:/sightings";
    }

    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        Location location;
        Hero hero;
        try {
            location = superHeroServiceLayer.getLocationById(Integer.parseInt(locationId));
            String heroId = request.getParameter("heroId");
            hero = superHeroServiceLayer.getHeroById(Integer.parseInt(heroId));
        } catch (ClassNoSuchRecordException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));

        String sightingDescription = request.getParameter("description");

        Sighting sighting = new Sighting();
        sighting.setId(Integer.parseInt(request.getParameter("id")));
        sighting.setDate(date);
        sighting.setDescription(sightingDescription);
        sighting.setHero(hero);
        sighting.setLocation(location);
        try {
            superHeroServiceLayer.updateSighting(sighting);
        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }

        return "redirect:/sightings";
    }

    @PostMapping("/deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        superHeroServiceLayer.deleteSightingById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/sightings";
    }
}
