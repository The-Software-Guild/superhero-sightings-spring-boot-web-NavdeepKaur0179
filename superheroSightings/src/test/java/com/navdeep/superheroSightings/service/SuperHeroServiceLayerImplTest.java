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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author kaurn
 */
@SpringBootTest
public class SuperHeroServiceLayerImplTest {

    @Autowired
    private SuperHeroServiceLayer serviceLayer;

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperPowerDao superPowerDao;

    public SuperHeroServiceLayerImplTest() {
//        heroDao = new HeroDaoDB();;
//        organizationDao = new OrganizationDaoDB();
//        locationDao = new LocationDaoDB();
//        sightingDao = new SightingDaoDB();
//        superPowerDao=new SuperPowerDaoDB();
        serviceLayer = new SuperHeroServiceLayerImpl(heroDao, locationDao, organizationDao, sightingDao, superPowerDao);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        if (heroDao.getAllHeros().size() > 0) {
            List<Hero> heros = serviceLayer.getAllHeros();
            for (Hero hero : heros) {
                serviceLayer.deleteHeroById(hero.getId());
            }
        }
        if (locationDao.getAllLocations().size() > 0) {
            List<Location> locations = serviceLayer.getAllLocations();
            for (Location location : locations) {
                serviceLayer.deleteLocationById(location.getId());
            }
        }
        if (organizationDao.getAllOrganizations().size() > 0) {
            List<Organization> organizations = serviceLayer.getAllOrganizations();
            for (Organization organization : organizations) {
                serviceLayer.deleteOrganizationbyId(organization.getId());
            }
        }

        if (sightingDao.getAllSightings().size() > 0) {
            List<Sighting> sightings = serviceLayer.getAllSightings();
            for (Sighting sighting : sightings) {
                serviceLayer.deleteSightingById(sighting.getId());
            }
        }
        if (superPowerDao.getAllSuperPowers().size() > 0) {
            List<SuperPower> superPowers = serviceLayer.getAllSuperPowers();
            for (SuperPower superPower : superPowers) {
                serviceLayer.deleteSuperPowerById(superPower.getId());
            }
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetLocationById() throws Exception {
        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        Location shouldBeTestLocation = serviceLayer.getLocationById(1);
        assertNotNull(shouldBeTestLocation);
        assertEquals(location, shouldBeTestLocation);
        //Invalid id
        try {
            Location anotherLocation = serviceLayer.getLocationById(2);
        } catch (ClassNoSuchRecordException e) {
            return;
        }
    }

    /**
     * Test of getAllLocations method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetAllLocations() throws Exception {
        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");

        List<Location> locations = serviceLayer.getAllLocations();
        assertEquals(1, locations.size());
        assertTrue(locations.contains(location));

        serviceLayer.deleteLocationById(1);
        try {
            locations = serviceLayer.getAllLocations();
        } catch (ClassEmptyListException e) {
            return;
        }
    }

    /**
     * Test of addLocation method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testAddValidLocation() throws Exception {
        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        try {
            serviceLayer.addLocation(location);
        } catch (ClassDataValidationException e) {
            fail("Location was valid , no exception was thrown");
        }
    }

    @Test
    public void testAddInValidLocation() throws Exception {
        Location location = new Location();
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        try {
            serviceLayer.addLocation(location);
        } catch (ClassDataValidationException e) {
            return;
        }
    }
//    @Test
//    public void testUpdateLocation() throws Exception {
//    }
//
//    /**
//     * Test of deleteLocationById method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testDeleteLocationById() {
//    }
//

    /**
     * Test of getHeroById method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetHeroById() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        Hero shouldBeTestHero = serviceLayer.getHeroById(1);
        assertNotNull(shouldBeTestHero);
        assertEquals(testHero, shouldBeTestHero);
        //Invalid id
        try {
            Hero anotherHero = serviceLayer.getHeroById(2);
        } catch (ClassNoSuchRecordException e) {
            return;
        }

    }

    /**
     * Test of getAllHeros method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetAllHeros() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        List<Hero> heroes = serviceLayer.getAllHeros();
        assertEquals(1, heroes.size());
        assertTrue(heroes.contains(testHero));

        serviceLayer.deleteHeroById(1);
        try {
            heroes = serviceLayer.getAllHeros();
        } catch (ClassEmptyListException e) {
            return;
        }
    }

    /**
     * Test of addHero method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testAddValidHero() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);

        try {
            serviceLayer.addHero(testHero);
        } catch (ClassDataValidationException e) {
            fail("Location was valid , no exception was thrown");
        }
    }

    @Test
    public void testAddInValidHero() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);

        try {
            serviceLayer.addHero(testHero);
        } catch (ClassDataValidationException e) {
            return;
        }
    }
//
//    /**
//     * Test of updateHero method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testUpdateHero() throws Exception {
//    }
//
//    /**
//     * Test of deleteHeroById method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testDeleteHeroById() {
//    }
//
//    /**
//     * Test of getAllMemberHerosOfOrganization method, of class
//     * SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testGetAllMemberHerosOfOrganization() {
//    }

    /**
     * Test of getLocationById method, of class SuperHeroServiceLayerImpl.
     */
//    /**
//     * Test of updateLocation method, of class SuperHeroServiceLayerImpl.
//     */
    /**
     * Test of getOrganizationById method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetOrganizationById() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        Organization shouldOrganization = serviceLayer.getOrganizationById(1);
        assertNotNull(shouldOrganization);
        assertEquals(shouldOrganization, organization);

        //Invalid Id
        try {
            shouldOrganization = serviceLayer.getOrganizationById(2);
        } catch (ClassNoSuchRecordException e) {
            return;
        }

    }

    /**
     * Test of getAllOrganizations method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetAllOrganizations() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizations = serviceLayer.getAllOrganizations();
        assertEquals(1, organizations.size());
        assertTrue(organizations.contains(organization));

        //Empty List Check
        serviceLayer.deleteOrganizationbyId(1);
        try {
            organizations = serviceLayer.getAllOrganizations();
        } catch (ClassEmptyListException e) {
            return;
        }
    }

    /**
     * Test of addOrganization method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testAddValidOrganization() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");

        Organization shouldOrganization = serviceLayer.addOrganization(organization);
        assertNotNull(shouldOrganization);
        assertEquals(shouldOrganization, organization);

    }

    @Test
    public void testAddInValidOrganization() throws Exception {
        Organization organization = new Organization();
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        try {
            Organization shouldOrganization = serviceLayer.addOrganization(organization);
        } catch (ClassDataValidationException e) {
            return;
        }

    }

//    /**
//     * Test of updateorganization method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testUpdateorganization() throws Exception {
//    }
//
//    /**
//     * Test of deleteOrganizationbyId method, of class
//     * SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testDeleteOrganizationbyId() {
//    }
//
//    /**
//     * Test of getAllOrganizationsOfHero method, of class
//     * SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testGetAllOrganizationsOfHero() {
//    }
//
    /**
     * Test of getSighingById method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetSighingById() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);

        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        location = serviceLayer.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = serviceLayer.addSighting(sighting);

        Sighting shouldSighting = serviceLayer.getSighingById(1);
        assertNotNull(shouldSighting);
        assertEquals(shouldSighting, sighting);

        //Invalid Id
        try {
            shouldSighting = serviceLayer.getSighingById(2);
        } catch (ClassNoSuchRecordException e) {
            return;
        }

    }

    /**
     * Test of getAllSightings method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testGetAllSightings() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        location = serviceLayer.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = serviceLayer.addSighting(sighting);

        List<Sighting> sightings = serviceLayer.getAllSightings();
        assertEquals(1, sightings.size());
        assertTrue(sightings.contains(sighting));

        //Empty List Check
        serviceLayer.deleteSightingById(1);
        try {
            sightings = serviceLayer.getAllSightings();
        } catch (ClassEmptyListException e) {
            return;
        }

    }

    /**
     * Test of addSighting method, of class SuperHeroServiceLayerImpl.
     */
    @Test
    public void testAddValidSighting() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);

        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        location = serviceLayer.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = serviceLayer.addSighting(sighting);

        try {
            serviceLayer.addSighting(sighting);
        } catch (ClassDataValidationException e) {
            fail("Location was valid , no exception was thrown");
        }
    }

    @Test
    public void testAddInValidSighting() throws Exception {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = serviceLayer.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Power Description");
        SuperPower superPowers = serviceLayer.addSuperPower(power);

        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = serviceLayer.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location Latitude");
        location.setLongitude("Test Location Latitude");
        location = serviceLayer.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = serviceLayer.addSighting(sighting);

        try {
            serviceLayer.addSighting(sighting);
        } catch (ClassDataValidationException e) {
            return;
        }
    }

//    /**
//     * Test of updateSighting method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testUpdateSighting() {
//    }
//
//    /**
//     * Test of deleteSightingById method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testDeleteSightingById() {
//    }
//
//    /**
//     * Test of getAllSightingByLocation method, of class
//     * SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testGetAllSightingByLocation() {
//    }
//
//    /**
//     * Test of getAllLocationsHeroSeen method, of class
//     * SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testGetAllLocationsHeroSeen() {
//    }
//
//    /**
//     * Test of getAllSightingByDate method, of class SuperHeroServiceLayerImpl.
//     */
//    @Test
//    public void testGetAllSightingByDate() {
//    }
//
//    private void assertNull() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
