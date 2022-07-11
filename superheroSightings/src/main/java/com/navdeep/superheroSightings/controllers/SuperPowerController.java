/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.SuperPower;
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
 * @author kaurn
 */
@Controller
public class SuperPowerController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    public SuperPowerController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    String exceptionErrorMessage = "";

    @GetMapping("superPowers")
    public String getSuperPowers(Model model) {
        try {
            List<SuperPower> superPowers = superHeroServiceLayer.getAllSuperPowers();
            model.addAttribute("superPowers", superPowers);
            return "superPowers";
        } catch (ClassEmptyListException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }

    }

    @PostMapping("/addSuperPower")
    public String addSuperPower(SuperPower superPower) {
        try {
            superHeroServiceLayer.addSuperPower(superPower);
            return "redirect:/superPowers";
        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
    }

    @PostMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request) {
        String superPowerName = request.getParameter("superPower");
        String superPowerDescription = request.getParameter("description");
        String superPowerId = request.getParameter("id");

        SuperPower superPower = new SuperPower();
        superPower.setId(Integer.parseInt(superPowerId));
        superPower.setSuperPower(superPowerName);
        superPower.setDescription(superPowerDescription);
        try {
            superHeroServiceLayer.updateSuperPower(superPower);
            return "redirect:/superPowers";

        } catch (ClassDataValidationException e) {
            exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
    }

    @PostMapping("/deleteSuperPower")
    public String deleteSuperPower(HttpServletRequest request) {
        superHeroServiceLayer.deleteSuperPowerById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/superPowers";
    }
}
