/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Sighting;
import com.navdeep.superheroSightings.service.ClassEmptyListException;
import com.navdeep.superheroSightings.service.SuperHeroServiceLayer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author kaurn
 */
@Controller
public class HomeController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    public HomeController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    String exceptionErrorMessage = "";

    @GetMapping("/")
    public String getSightingsForHome(Model model) {
        List<Sighting> sightings;
        try {
            sightings = superHeroServiceLayer.getAllSightings();
            if (sightings.size() > 10) {
                sightings = sightings.subList(0, 10);
            }
        } catch (ClassEmptyListException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        model.addAttribute("sightings", sightings);
        List<Location> locations=new ArrayList<>();
        sightings.forEach(sighting->locations.add(sighting.getLocation()));
        model.addAttribute("locations", locations);
        return "index";
    }

}
