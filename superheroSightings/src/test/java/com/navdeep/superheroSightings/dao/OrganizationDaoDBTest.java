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
public class OrganizationDaoDBTest {

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

    public OrganizationDaoDBTest() {
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
     * Test of getOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganizationById() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(fromDao, organization);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Test Organization2  Name");
        organization2.setDescription("Test Organization 2 description");
        organization2.setAddress("Test Organization 2  address");
        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    /**
     * Test of updateorganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateorganization() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organization.setName("Updated Test OrganizationName");
        organizationDao.updateorganization(organization);
        assertNotEquals(organization, fromDao);
    }

    /**
     * Test of deleteOrganizationbyId method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationbyId() {
        List<Hero> heros = new ArrayList<>();

        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);
        assertNotNull(organizationDao.getOrganizationById(organization.getId()));

        organizationDao.deleteOrganizationbyId(organization.getId());
        assertNull(organizationDao.getOrganizationById(organization.getId()));
    }

    /**
     * Test of getAllOrganizationsOfHero method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizationsOfHero() {
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        organization = organizationDao.addOrganization(organization);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
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

        Organization organization2 = new Organization();
        organization2.setName("Test Organization2  Name");
        organization2.setDescription("Test Organization 2 description");
        organization2.setAddress("Test Organization 2  address");
        organization2 = organizationDao.addOrganization(organization2);

        organizationList.add(organization2);
        testHero.setOrganizations(organizationList);
        heroDao.updateHero(testHero);

        List<Organization> organizationOfHero = organizationDao.getAllOrganizationsOfHero(testHero);
        assertEquals(2, organizationOfHero.size());
    }

}
