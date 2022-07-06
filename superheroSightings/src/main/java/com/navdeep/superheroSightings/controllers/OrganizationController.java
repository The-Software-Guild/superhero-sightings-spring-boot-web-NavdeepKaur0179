/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.dao.HeroDao;
import com.navdeep.superheroSightings.dao.LocationDao;
import com.navdeep.superheroSightings.dao.OrganizationDao;
import com.navdeep.superheroSightings.dao.OrganizationDao;
import com.navdeep.superheroSightings.dao.SightingDao;
import com.navdeep.superheroSightings.entities.Organization;
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
public class OrganizationController {
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    @GetMapping("organizations")
    public String getOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

    @PostMapping("/addOrganization")
    public String addOrganization(Organization organization) {
        organizationDao.addOrganization(organization);
        return "redirect:/organizations";
    }

    @PostMapping("editOrganization")
    public String editOrganization(HttpServletRequest request) {
        String organizationName = request.getParameter("name");
        String organizationDescription = request.getParameter("description");
        String organizationAddress = request.getParameter("address");        
        String organizationId = request.getParameter("id");

        Organization organization = new Organization();
        organization.setId(Integer.parseInt(organizationId));
        organization.setName(organizationName);
        organization.setDescription(organizationDescription);
        organization.setAddress(organizationAddress);       
        organizationDao.updateorganization(organization);
        return "redirect:/organizations";
    }

    @PostMapping("/deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        organizationDao.deleteOrganizationbyId(Integer.parseInt(request.getParameter("id")));
        return "redirect:/organizations";
    }
}
