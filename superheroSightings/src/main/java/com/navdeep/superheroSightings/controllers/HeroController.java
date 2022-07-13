/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Organization;
import com.navdeep.superheroSightings.entities.SuperPower;
import com.navdeep.superheroSightings.service.ClassDataValidationException;
import com.navdeep.superheroSightings.service.ClassEmptyListException;
import com.navdeep.superheroSightings.service.ClassNoSuchRecordException;
import com.navdeep.superheroSightings.service.SuperHeroServiceLayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class HeroController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;

    public HeroController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
//    @Autowired
//    HeroDao heroDao;
//
//    @Autowired
//    LocationDao locationDao;
//
//    @Autowired
//    OrganizationDao organizationDao;
//
//    @Autowired
//    SightingDao sightingDao;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();   
    Map<Integer, List<Location>> heroLocations = new HashMap<>();
    List<Hero> heros;
    List<Organization> organizations;
    List<SuperPower> superPowers;

    @GetMapping("heros")
    public String getHeros(Model model) {
        try {
            heros = superHeroServiceLayer.getAllHeros();
            for (Hero hero : heros) {
                heroLocations.put(hero.getId(), superHeroServiceLayer.getAllLocationsHeroSeen(hero));
            }
            model.addAttribute("heroLocations", heroLocations);
            model.addAttribute("heros", heros);
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        try {
           organizations = superHeroServiceLayer.getAllOrganizations();
            model.addAttribute("organizations", organizations);
            superPowers = superHeroServiceLayer.getAllSuperPowers();
            model.addAttribute("superPowers", superPowers);
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        //model.addAttribute("errors", violations);
        return "heros";
    }

    @PostMapping("/addHero")
    public String addHero(Hero hero, HttpServletRequest request,Model model) {
        String[] organizationIds = request.getParameterValues("organizationId");
        List<Organization> organizations = new ArrayList<>();
        SuperPower superPower;
        if (organizationIds!=null) {
           try {
            for (String organizationId : organizationIds) {
                organizations.add(superHeroServiceLayer.getOrganizationById(Integer.parseInt(organizationId)));
            }
            superPower = superHeroServiceLayer.getSuperPowerById(Integer.parseInt(request.getParameter("superPowerId")));
        } catch (ClassNoSuchRecordException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        hero.setOrganizations(organizations);  
        hero.setSuperPowers(superPower);
        }
        //validate inputs before sending to Dao
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        model.addAttribute("errors", violations);
        if (violations.isEmpty()) {
            try {
                superHeroServiceLayer.addHero(hero);
            } catch (ClassDataValidationException e) {
                LocationController.exceptionErrorMessage = e.getMessage();
                return "redirect/errorPage";
            }
        }
        return "redirect:/heros";
    }

    @PostMapping("editHero")
    public String editHero(HttpServletRequest request) {
        String[] organizationIds = request.getParameterValues("organizationId");
        String heroName = request.getParameter("name");
        String heroDescription = request.getParameter("description");
        String superPowerId = request.getParameter("superPowerId");
        String heroId = request.getParameter("id");
        SuperPower superPower;
        List<Organization> organizations;
        try {
            superPower = superHeroServiceLayer.getSuperPowerById(Integer.parseInt(superPowerId));
            organizations = new ArrayList<>();
            for (String organizationId : organizationIds) {
                Organization organization = superHeroServiceLayer.getOrganizationById(Integer.parseInt(organizationId));
                organizations.add(organization);
            }
        } catch (ClassNoSuchRecordException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        Hero hero = new Hero();
        hero.setId(Integer.parseInt(heroId));
        hero.setName(heroName);
        hero.setDescription(heroDescription);
        hero.setSuperPowers(superPower);
        hero.setOrganizations(organizations);
        try {
            superHeroServiceLayer.updateHero(hero);
        } catch (ClassDataValidationException e) {
        }
        return "redirect:/heros";
    }

    @PostMapping("/deleteHero")
    public String deleteHero(HttpServletRequest request) {
        superHeroServiceLayer.deleteHeroById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/heros";
    }

    @PostMapping("herosByOrganization")
    public String herosByOrganization(HttpServletRequest request, Model model) {
        Organization organization;
        try {
            organization = superHeroServiceLayer.getOrganizationById(Integer.parseInt(request.getParameter("organizationOfHeros")));
        } catch (ClassNoSuchRecordException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        List<Hero> heros = superHeroServiceLayer.getAllMemberHerosOfOrganization(organization);
        for (Hero hero : heros) {
            heroLocations.put(hero.getId(), superHeroServiceLayer.getAllLocationsHeroSeen(hero));
        }
        model.addAttribute("heroLocations", heroLocations);
        model.addAttribute("heros", heros);
        try {
           organizations = superHeroServiceLayer.getAllOrganizations();
            model.addAttribute("organizations", organizations);
            superPowers = superHeroServiceLayer.getAllSuperPowers();
            model.addAttribute("superPowers", superPowers);
        } catch (ClassEmptyListException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect/errorPage";
        }
        return "heros";
    }

}
