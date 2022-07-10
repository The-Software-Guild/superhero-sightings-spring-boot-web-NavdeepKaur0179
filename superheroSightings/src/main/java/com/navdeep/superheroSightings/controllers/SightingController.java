/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.dao.HeroDao;
import com.navdeep.superheroSightings.dao.LocationDao;
import com.navdeep.superheroSightings.dao.OrganizationDao;
import com.navdeep.superheroSightings.dao.SightingDao;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Sighting;
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
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("sightings")
    public String getSightings(Model model) {
        List<Hero> heroes = heroDao.getAllHeros();
        model.addAttribute("heroes", heroes);
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("/addSighting")
    public String addSighting(HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        String heroId = request.getParameter("heroId");
        Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        sighting.setDescription(request.getParameter("description"));
        sightingDao.addSighting(sighting);
        return "redirect:/sightings";
    }


    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        String heroId = request.getParameter("heroId");
        Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));

        String sightingDescription = request.getParameter("description");

        Sighting sighting = new Sighting();
        sighting.setId(Integer.parseInt(request.getParameter("id")));
        sighting.setDate(date);
        sighting.setDescription(sightingDescription);
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }

    @PostMapping("/deleteSighting")
    public String deleteSighting(HttpServletRequest request)
    {
        sightingDao.deleteSightingById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/sightings";
    } 
}
