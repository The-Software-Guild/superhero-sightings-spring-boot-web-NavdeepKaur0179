/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.entities.Organization;
import com.navdeep.superheroSightings.service.ClassDataValidationException;
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
public class OrganizationController {

    @Autowired
    final SuperHeroServiceLayer superHeroServiceLayer;
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    public OrganizationController(SuperHeroServiceLayer superHeroServiceLayer) {
        this.superHeroServiceLayer = superHeroServiceLayer;
    }
    @GetMapping("organizations")
    public String getOrganizations(Model model) {
        try {
            List<Organization> organizations = superHeroServiceLayer.getAllOrganizations();
            model.addAttribute("organizations", organizations);
        } catch (Exception e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        return "organizations";
    }

    @PostMapping("/addOrganization")
    public String addOrganization(Organization organization,Model model) {
        violations = validate.validate(organization);
        model.addAttribute("errors", violations);
        if (!violations.isEmpty()) {
            return "errorPage";
        }
        try {
            superHeroServiceLayer.addOrganization(organization);
        } catch (ClassDataValidationException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        return "redirect:/organizations";
    }

    @PostMapping("editOrganization")
    public String editOrganization(HttpServletRequest request,Model model) {        
        String organizationName = request.getParameter("name");
        String organizationDescription = request.getParameter("description");
        String organizationAddress = request.getParameter("address");
        String organizationId = request.getParameter("id");

        Organization organization = new Organization();
        organization.setId(Integer.parseInt(organizationId));
        organization.setName(organizationName);
        organization.setDescription(organizationDescription);
        organization.setAddress(organizationAddress);
        //check for violations
        violations = validate.validate(organization);
        model.addAttribute("errors", violations);
        if (!violations.isEmpty()) {
            return "errorPage";
        }
        try {
            superHeroServiceLayer.updateorganization(organization);
        } catch (ClassDataValidationException e) {
            LocationController.exceptionErrorMessage = e.getMessage();
            return "redirect:/errorPage";
        }
        return "redirect:/organizations";
    }

    @PostMapping("/deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        superHeroServiceLayer.deleteOrganizationbyId(Integer.parseInt(request.getParameter("id")));
        return "redirect:/organizations";
    }
}
