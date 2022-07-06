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
import com.navdeep.superheroSightings.entities.Organization;
import java.util.ArrayList;
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
public class HeroController {
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("heros")
    public String getHeros(Model model)
    {
       List<Hero> heros=heroDao.getAllHeros();
       model.addAttribute("heros", heros);
       List<Organization> organizations=organizationDao.getAllOrganizations();
       model.addAttribute("organizations",organizations);
       return "heros"; 
    }
    
    @PostMapping("/addHero")
    public String addHero(Hero hero,HttpServletRequest request)
    {
        String[] organizationIds=request.getParameterValues("organizationId");        
        List<Organization> organizations=new ArrayList<>();
        for (String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }
        hero.setOrganizations(organizations);
        heroDao.addHero(hero);
        return "redirect:/heros";
    }
    @PostMapping("editHero")
    public String editHero(HttpServletRequest request) {
        String[] organizationIds=request.getParameterValues("organizationId");
        String heroName=request.getParameter("name");
        String heroDescription=request.getParameter("description");
        String superPower=request.getParameter("superPower");
        String heroId=request.getParameter("id");
        
        List<Organization> organizations = new ArrayList<>();
        for (String organizationId : organizationIds) {
            Organization organization=organizationDao.getOrganizationById(Integer.parseInt(organizationId));
            organizations.add(organization);
        }
        Hero hero=new Hero();
        hero.setId(Integer.parseInt(heroId));
        hero.setName(heroName);
        hero.setDescription(heroDescription);
        hero.setSuperPower(superPower);
        hero.setOrganizations(organizations);
        heroDao.updateHero(hero);
        return "redirect:/heros";
    }
    
    @PostMapping("/deleteHero")
    public String deleteHero(HttpServletRequest request)
    {
        heroDao.deleteHeroById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/heros";
    }
}
