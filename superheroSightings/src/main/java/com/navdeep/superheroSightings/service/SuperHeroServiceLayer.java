/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Organization;
import com.navdeep.superheroSightings.entities.Sighting;
import com.navdeep.superheroSightings.entities.SuperPower;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author kaurn
 */
public interface SuperHeroServiceLayer {
    //Hero
    Hero getHeroById(int id) throws ClassNoSuchRecordException;
    List<Hero> getAllHeros() throws ClassEmptyListException;
    Hero addHero(Hero hero) throws ClassDataValidationException;
    void updateHero(Hero hero) throws ClassDataValidationException;
    void deleteHeroById(int id);  
    List<Hero> getAllMemberHerosOfOrganization(Organization organization);
    
    //Location
    Location getLocationById(int id)throws ClassNoSuchRecordException;;
    List<Location> getAllLocations() throws ClassEmptyListException;
    Location addLocation(Location location) throws ClassDataValidationException;
    void updateLocation(Location location) throws ClassDataValidationException;
    void deleteLocationById(int id);
    
    //Organization
    Organization getOrganizationById(int id) throws ClassNoSuchRecordException;;
    List<Organization> getAllOrganizations() throws ClassEmptyListException;
    Organization addOrganization(Organization organization)throws ClassDataValidationException;
    void updateorganization(Organization organization) throws ClassDataValidationException;
    void deleteOrganizationbyId(int id);
   
    List<Organization> getAllOrganizationsOfHero(Hero hero);
    
    //sighting
    Sighting getSighingById(int id) throws ClassNoSuchRecordException;
    List<Sighting> getAllSightings() throws ClassEmptyListException;
    Sighting addSighting(Sighting sighting)throws ClassDataValidationException;
    void updateSighting(Sighting sighting)throws ClassDataValidationException;
    void deleteSightingById(int id);
    List<Sighting> getAllSightingByLocation(Location location);
    List<Location> getAllLocationsHeroSeen(Hero hero);
    List<Sighting> getAllSightingByDate(LocalDateTime date);
    
    //SuperPower
    SuperPower getSuperPowerById(int id) throws ClassNoSuchRecordException;
    List<SuperPower> getAllSuperPowers() throws ClassEmptyListException;
    SuperPower addSuperPower(SuperPower superPower)throws ClassDataValidationException;
    void updateSuperPower(SuperPower superPower)throws ClassDataValidationException;
    void deleteSuperPowerById(int id);
}
