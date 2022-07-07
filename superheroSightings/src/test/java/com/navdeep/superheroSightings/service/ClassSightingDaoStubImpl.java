/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.dao.SightingDao;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Organization;
import com.navdeep.superheroSightings.entities.Sighting;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaurn
 */
public class ClassSightingDaoStubImpl implements SightingDao {

    Sighting sighting;

    public ClassSightingDaoStubImpl() {
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        Hero testHero = new Hero();
        testHero.setId(1);
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);

        Location location = new Location();
        location.setId(1);
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatlong("Test Location latlong");

        sighting = new Sighting();
        sighting.setId(1);
        sighting.setDate(LocalDate.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
    }

    public ClassSightingDaoStubImpl(Sighting sighting) {
        this.sighting = sighting;
    }

    @Override
    public Sighting getSighingById(int id) {
        if (id == sighting.getId()) {
            return sighting;
        } else {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(sighting);
        return sightings;
    }

    @Override
    public Sighting addSighting(Sighting sightingToAdd) {
        if (sighting.equals(sightingToAdd)) {
            return sighting;
        } else {
            return null;
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
    }

    @Override
    public void deleteSightingById(int id) {
    }

    @Override
    public List<Sighting> getAllSightingByLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Location> getAllLocationsHeroSeen(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getAllSightingByDate(LocalDate date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
