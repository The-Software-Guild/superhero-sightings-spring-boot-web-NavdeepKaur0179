/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.dao.HeroDao;
import com.navdeep.superheroSightings.dao.LocationDao;
import com.navdeep.superheroSightings.dao.OrganizationDao;
import com.navdeep.superheroSightings.dao.SightingDao;
import com.navdeep.superheroSightings.dao.SuperPowerDao;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Organization;
import com.navdeep.superheroSightings.entities.Sighting;
import com.navdeep.superheroSightings.entities.SuperPower;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author kaurn
 */
@Component
public class SuperHeroServiceLayerImpl implements SuperHeroServiceLayer {

    final private HeroDao heroDao;
    final private LocationDao locationDao;
    final private OrganizationDao organizationDao;
    final private SightingDao sightingDao;
    final private SuperPowerDao superPowerDao;

    public SuperHeroServiceLayerImpl(HeroDao heroDao, LocationDao locationDao,
            OrganizationDao organizationDao, SightingDao sightingDao, SuperPowerDao superPowerDao) {
        this.heroDao = heroDao;
        this.locationDao = locationDao;
        this.organizationDao = organizationDao;
        this.sightingDao = sightingDao;
        this.superPowerDao = superPowerDao;
    }

    @Override
    public Hero getHeroById(int id) throws ClassNoSuchRecordException {
        Hero hero = heroDao.getHeroById(id);
        if (hero != null) {
            return hero;
        } else {
            throw new ClassNoSuchRecordException("No such Hero Exist");
        }
    }

    @Override
    public List<Hero> getAllHeros() throws ClassEmptyListException {
        List<Hero> heros = heroDao.getAllHeros();
        if (heros.isEmpty()) {
            throw new ClassEmptyListException("Empty Hero List");
        }
        return heros;
    }

    @Override
    public Hero addHero(Hero hero) throws ClassDataValidationException {
        if (!validateHeroData(hero)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Organizations, superPower] are required.");
        }
        return heroDao.addHero(hero);
    }

    @Override
    public void updateHero(Hero hero) throws ClassDataValidationException {
        if (!validateHeroData(hero)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Organizations, superPower] are required.");
        }
        heroDao.updateHero(hero);
    }

    @Override
    public void deleteHeroById(int id) {
        heroDao.deleteHeroById(id);
    }

    @Override
    public List<Hero> getAllMemberHerosOfOrganization(Organization organization) {
        return heroDao.getAllMemberHerosOfOrganization(organization);
    }

    @Override
    public Location getLocationById(int id) throws ClassNoSuchRecordException {
        Location location = locationDao.getLocationById(id);
        if (location != null) {
            return location;
        } else {
            throw new ClassNoSuchRecordException("No such Location Exist");
        }
    }

    @Override
    public List<Location> getAllLocations() throws ClassEmptyListException {
        List<Location> locations = locationDao.getAllLocations();
        if (locations.isEmpty()) {
            throw new ClassEmptyListException("Empty Location List");
        }
        return locations;
    }

    @Override
    public Location addLocation(Location location) throws ClassDataValidationException {
        if (!validateLocationData(location)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Address, Latitude Longitude] are required.");
        }
        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) throws ClassDataValidationException {
        if (!validateLocationData(location)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Address, Latitude Longitude] are required.");
        }
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }

    @Override
    public Organization getOrganizationById(int id) throws ClassNoSuchRecordException {
        Organization organization = organizationDao.getOrganizationById(id);
        if (organization != null) {
            return organization;
        } else {
            throw new ClassNoSuchRecordException("No such Organozation Exist");
        }
    }

    @Override
    public List<Organization> getAllOrganizations() throws ClassEmptyListException {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        if (organizations.isEmpty()) {
            throw new ClassEmptyListException("Empty Organization List");
        }
        return organizations;
    }

    @Override
    public Organization addOrganization(Organization organization) throws ClassDataValidationException {
        if (!validateOrganizationData(organization)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Address ] are required.");
        }
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateorganization(Organization organization) throws ClassDataValidationException {
        if (!validateOrganizationData(organization)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Name, Address ] are required.");
        }
        organizationDao.updateorganization(organization);
    }

    @Override
    public void deleteOrganizationbyId(int id) {
        organizationDao.deleteOrganizationbyId(id);
    }

    @Override
    public List<Organization> getAllOrganizationsOfHero(Hero hero) {
        return organizationDao.getAllOrganizationsOfHero(hero);
    }

    @Override
    public Sighting getSighingById(int id) throws ClassNoSuchRecordException {
        Sighting sighting = sightingDao.getSighingById(id);
        if (sighting != null) {
            return sighting;
        } else {
            throw new ClassNoSuchRecordException("No such Sighting Exist");
        }
    }

    @Override
    public List<Sighting> getAllSightings() throws ClassEmptyListException {
        List<Sighting> sightings = sightingDao.getAllSightings();
        if (sightings.isEmpty()) {
            throw new ClassEmptyListException("Empty Sighting List");
        }
        return sightings;
    }

    @Override
    public Sighting addSighting(Sighting sighting) throws ClassDataValidationException {
        if (!validateSightingData(sighting)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Location, hero, date ] are required and [date] cannot be of future.");
        }
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) throws ClassDataValidationException {
        if (!validateSightingData(sighting)) {
            throw new ClassDataValidationException(
                    "ERROR: All fields [ Location, hero, date ] are required and [date] cannot be of future.");
        }
        sightingDao.updateSighting(sighting);

    }

    @Override
    public void deleteSightingById(int id) {
        sightingDao.deleteSightingById(id);
    }

    @Override
    public List<Sighting> getAllSightingByLocation(Location location) {
        List<Sighting> sightings = sightingDao.getAllSightingByLocation(location);
        return sightings;
    }

    @Override
    public List<Location> getAllLocationsHeroSeen(Hero hero) {
        return sightingDao.getAllLocationsHeroSeen(hero);
    }

    @Override
    public List<Sighting> getAllSightingByDate(LocalDateTime date) {
        List<Sighting> sightings = sightingDao.getAllSightingByDate(date);
        return sightings;
    }

    private boolean validateHeroData(Hero hero) throws ClassDataValidationException {
        if (hero.getName() == null || hero.getName().trim().length() == 0
                || hero.getSuperPowers() == null || hero.getOrganizations().isEmpty()) {

            return false;
        }
        return true;
    }

    private boolean validateLocationData(Location location) {
        if (location.getName() == null || location.getName().trim().length() == 0
                || location.getAddress() == null || location.getAddress().trim().length() == 0
                || location.getLongitude() == null || location.getLongitude().trim().length() == 0
                || location.getLatitude() == null || location.getLatitude().trim().length() == 0) {

            return false;
        }
        return true;
    }

    private boolean validateOrganizationData(Organization organization) {
        if (organization.getName() == null || organization.getName().trim().length() == 0
                || organization.getAddress() == null || organization.getAddress().trim().length() == 0) {
            return false;
        }
        return true;
    }

    private boolean validateSightingData(Sighting sighting) {
        if (sighting.getLocation() == null
                || sighting.getHero() == null || sighting.getDate() == null) {
            return false;
        } else if (sighting.getDate() != null) {
            if (sighting.getDate().isAfter(LocalDateTime.now())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public SuperPower getSuperPowerById(int id) throws ClassNoSuchRecordException {
        SuperPower superPower = superPowerDao.getSuperPowerById(id);
        if (superPower != null) {
            return superPower;
        } else {
            throw new ClassNoSuchRecordException("No such Sighting Exist");
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() throws ClassEmptyListException {
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        if (superPowers.isEmpty()) {
            throw new ClassEmptyListException("Empty Sighting List");
        }
        return superPowers;
    }

    @Override
    public SuperPower addSuperPower(SuperPower superPower) throws ClassDataValidationException {
        if (!validateSuperPowerData(superPower)) {
            throw new ClassDataValidationException(
                    "ERROR: Field [ name ] is required.");
        }
        return superPowerDao.addSuperPower(superPower);
    }

    @Override
    public void updateSuperPower(SuperPower superPower) throws ClassDataValidationException {
        if (!validateSuperPowerData(superPower)) {
            throw new ClassDataValidationException(
                    "ERROR: Field [ name ] is required.");
        }
        superPowerDao.updateSuperPower(superPower);
    }

    @Override
    public void deleteSuperPowerById(int id) {
        superPowerDao.deleteSuperPowerById(id);
    }

    private boolean validateSuperPowerData(SuperPower superPower) {
        if (superPower.getSuperPower() == null || superPower.getSuperPower().trim().length() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Sighting> getAllSightingsByHero(Hero hero) {
        List<Sighting> sightings = sightingDao.getAllSightingsByHero(hero);
        return sightings;
    }
}
