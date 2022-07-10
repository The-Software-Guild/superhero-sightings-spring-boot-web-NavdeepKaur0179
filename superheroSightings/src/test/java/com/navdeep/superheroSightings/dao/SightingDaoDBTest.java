/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

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
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author nkaur
 */
@SpringBootTest
public class SightingDaoDBTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperPowerDao superPowerDao;

    public SightingDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Hero> heros = heroDao.getAllHeros();
        for (Hero hero : heros) {
            heroDao.deleteHeroById(hero.getId());
        }
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationbyId(organization.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superPowerDao.deleteSuperPowerById(superPower.getId());
        }

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSighingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighingById() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSighingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Organization organization2 = new Organization();
        organization2.setName("Test Organization2 Name");
        organization2.setDescription("Test Organization 2 description");
        organization2.setAddress("Test Organization 2 address");

        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizationList2 = new ArrayList<>();
        organizationList2.add(organization2);

        Hero testHero2 = new Hero();
        testHero2.setName("test Hero Name 2");
        testHero2.setDescription("Test Hero 2 Description");
        testHero2.setSuperPowers(superPowers);
        testHero2.setOrganizations(organizationList);
        testHero2 = heroDao.addHero(testHero2);

        Location location2 = new Location();
        location2.setName("Test Location 2 name");
        location2.setDescription("Test Location 2 description");
        location2.setAddress("Test Location  2 address");
        location2.setLatitude("Test Location latitude");
        location2.setLongitude("Test Location longitude");
        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.now().withNano(0));
        sighting2.setHero(testHero2);
        sighting2.setLocation(location2);
        sighting2.setDescription("Test Sighting 2 description");

        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Hero updatedHero = new Hero();
        updatedHero.setName("test upadteHero Name");
        updatedHero.setDescription("Test upadteHero Description");
        updatedHero.setSuperPowers(superPowers);
        updatedHero.setOrganizations(organizationList);
        updatedHero = heroDao.addHero(updatedHero);

        Sighting fromDao = sightingDao.getSighingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setHero(updatedHero);
        sightingDao.updateSighting(sighting);

        assertNotEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        sightingDao.deleteSightingById(sighting.getId());
        assertNull(sightingDao.getSighingById(sighting.getId()));
    }

    /**
     * Test of getAllSightingByLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingByLocation() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Hero testHero2 = new Hero();
        testHero2.setName("test Hero 2 Name");
        testHero2.setDescription("Test Hero 2 Description");
        testHero2.setSuperPowers(superPowers);
        testHero2.setOrganizations(organizationList);
        testHero2 = heroDao.addHero(testHero2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.now().withNano(0));
        sighting2.setHero(testHero2);
        sighting2.setLocation(location);
        sighting2.setDescription("Test Sighting 2 description");

        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightingByLocation(location);
        assertEquals(2, sightings.size());
        assertEquals(sightings.get(0).getLocation(), location);
        assertEquals(sightings.get(1).getLocation(), location);
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of getAllLocationsHeroSeen method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllLocationsHeroSeen() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().withNano(0).minusDays(1));
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Location location2 = new Location();
        location2.setName("Test Location 2 name");
        location2.setDescription("Test Location 2 description");
        location2.setAddress("Test Location  2 address");
        location2.setLatitude("Test Location latitude");
        location2.setLongitude("Test Location longitude");
        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.now().withNano(0));
        sighting2.setHero(testHero);
        sighting2.setLocation(location2);
        sighting2.setDescription("Test Sighting 2 description");

        sighting2 = sightingDao.addSighting(sighting2);

        List<Location> locations = sightingDao.getAllLocationsHeroSeen(testHero);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));

    }

    /**
     * Test of getAllSightingByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingByDate() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization=organizationDao.addOrganization(organization);
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("test superPower Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        LocalDateTime dateTime=LocalDateTime.now().withNano(0);
        Sighting sighting = new Sighting();
        sighting.setDate(dateTime);
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription("Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Hero testHero2 = new Hero();
        testHero2.setName("test Hero 2 Name");
        testHero2.setDescription("Test Hero 2 Description");
        testHero2.setSuperPowers(superPowers);
        testHero2.setOrganizations(organizationList);
        testHero2 = heroDao.addHero(testHero2);

        Location location2 = new Location();
        location2.setName("Test Location 2 name");
        location2.setDescription("Test Location 2 description");
        location2.setAddress("Test Location  2 address");
        location2.setLatitude("Test Location latitude");
        location2.setLongitude("Test Location longitude");
        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(dateTime);
        sighting2.setHero(testHero2);
        sighting2.setLocation(location2);
        sighting2.setDescription("Test Sighting 2 description");

        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightingByDate(dateTime);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }
}
