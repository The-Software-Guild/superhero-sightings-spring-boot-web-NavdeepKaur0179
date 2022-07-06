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
import com.navdeep.superheroSightings.entities.Location;
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
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("locations")
    public String getLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("/addLocation")
    public String addLocation(Location location) {
        locationDao.addLocation(location);
        return "redirect:/locations";
    }

//    @GetMapping("location")
//    public String hero(Integer id)
//    {
//        return "redirect:/heros";
//    }  
    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        String locationName = request.getParameter("name");
        String locationDescription = request.getParameter("description");
        String locationAddress = request.getParameter("address");
        String locationLatLong = request.getParameter("latlong");
        String locationId = request.getParameter("id");

        Location location = new Location();
        location.setId(Integer.parseInt(locationId));
        location.setName(locationName);
        location.setDescription(locationDescription);
        location.setAddress(locationAddress);
        location.setLatlong(locationLatLong);
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

    @PostMapping("/deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        locationDao.deleteLocationById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/locations";
    }
}
