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
public class HeroDaoDBTest {

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

    public HeroDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        //Empty the database tables
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
     * Test of getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHeroById() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super power description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);

        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Hero fromDao = heroDao.getHeroById(testHero.getId());
        assertEquals(testHero, fromDao);

    }

    /**
     * Test of getAllHeros method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeros() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super Power Description");
        SuperPower superPowers = superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("test Hero Name 2");
        testHero2.setDescription("Test Hero Description 2");
        testHero2.setSuperPowers(superPowers);
        testHero2.setOrganizations(organizationList);
        testHero2 = heroDao.addHero(testHero2);

        List<Hero> heros = heroDao.getAllHeros();
        assertEquals(2, heros.size());
        assertTrue(heros.contains(testHero));
        assertTrue(heros.contains(testHero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

       SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        SuperPower superPowers=superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Hero fromDao = heroDao.getHeroById(testHero.getId());

        testHero.setName("updated Hero Test Name");
        heroDao.updateHero(testHero);

        assertNotEquals(fromDao, testHero);

        fromDao = heroDao.getHeroById(testHero.getId());
        assertEquals(fromDao, testHero);
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

       SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        SuperPower superPowers=superPowerDao.addSuperPower(power);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        List<Hero> heros = new ArrayList<>();
        heros.add(testHero);

        Location location = new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatitude("Test Location latitude");
        location.setLongitude("Test Location longitude");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription(" Test Sighting description");
        sighting = sightingDao.addSighting(sighting);

        Hero fromDao = heroDao.getHeroById(testHero.getId());
        assertNotNull(fromDao);

        heroDao.deleteHeroById(testHero.getId());
        fromDao = heroDao.getHeroById(testHero.getId());
        assertNull(fromDao);

    }

    @Test
    public void testGetAllMemberHerosOfOrganization() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        Organization OrganizationfromDao = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(OrganizationfromDao);

        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        SuperPower superPowers = superPowerDao.addSuperPower(power);

//        List<SuperPower> superPowers = new ArrayList<>();
//        superPowers.add(superPowerFromDao);
        Hero testHero = new Hero();

        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPowers(superPowers);
        testHero.setOrganizations(organizationList);
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("test Hero Name 2");
        testHero2.setDescription("Test Hero Description 2");
        testHero2.setSuperPowers(superPowers);
        testHero2.setOrganizations(organizationList);
        testHero2 = heroDao.addHero(testHero2);

        List<Hero> heros = heroDao.getAllMemberHerosOfOrganization(organization);
        assertEquals(2, heros.size());
        assertTrue(heros.contains(testHero));
        assertTrue(heros.contains(testHero2));
    }
}
