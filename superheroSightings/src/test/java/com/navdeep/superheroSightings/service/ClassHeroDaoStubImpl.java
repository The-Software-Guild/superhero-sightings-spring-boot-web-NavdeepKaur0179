/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.dao.HeroDao;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaurn
 */
public class ClassHeroDaoStubImpl implements HeroDao {

    Hero onlyHero;
    public ClassHeroDaoStubImpl()
    {
        Organization organization=new Organization();
        organization.setId(1);
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");        
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        onlyHero = new Hero();
        onlyHero.setId(1);
        onlyHero.setName("test Hero Name 1");
        onlyHero.setDescription("Test Hero Description");
        onlyHero.setSuperPower("test SuperPower");
        onlyHero.setOrganizations(organizationList);
    }

    public ClassHeroDaoStubImpl(Hero onlyHero) {
        this.onlyHero = onlyHero;
    }

    @Override
    public Hero getHeroById(int id) {
        if(id == onlyHero.getId())
        {
            return onlyHero;
        }
        else{
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeros() {
        List<Hero> heroes=new ArrayList<>();
        heroes.add(onlyHero);
        return heroes;
    }

    @Override
    public Hero addHero(Hero hero) {
        if(hero.equals(onlyHero))
        {
            return onlyHero;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void updateHero(Hero hero) {
    }

    @Override
    public void deleteHeroById(int id) {
    }

    @Override
    public List<Hero> getAllMemberHerosOfOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
