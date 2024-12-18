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
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class SuperPowerDaoDBTest {

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

    public SuperPowerDaoDBTest() {
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
     * Test of getSuperPowerById method, of class SuperPowerDaoDB.
     */
    @Test
    public void testAddAndGetSuperPowerById() {
        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super power description");
        power = superPowerDao.addSuperPower(power);

        SuperPower powerFromDao = superPowerDao.getSuperPowerById(power.getId());
        assertEquals(power, powerFromDao);
    }

    /**
     * Test of getAllSuperPowers method, of class SuperPowerDaoDB.
     */
    @Test
    public void testGetAllSuperPowers() {
        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super power description");
        power = superPowerDao.addSuperPower(power);

        SuperPower power2 = new SuperPower();
        power2.setSuperPower("test superPower 2");
        power2.setDescription("Super power description 2");
        power2 = superPowerDao.addSuperPower(power2);

        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        assertEquals(2, superPowers.size());
        assertTrue(superPowers.contains(power));
        assertTrue(superPowers.contains(power2));
    }

    
    /**
     * Test of updateSuperPower method, of class SuperPowerDaoDB.
     */
    @Test
    public void testUpdateSuperPower() {
        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super power description");
        power = superPowerDao.addSuperPower(power);
        
        SuperPower powerFromDao = superPowerDao.getSuperPowerById(power.getId());

        power.setDescription("Updated SuperPower Description");
        superPowerDao.updateSuperPower(power);

        assertNotEquals(powerFromDao, power);

        powerFromDao = superPowerDao.getSuperPowerById(power.getId());
        assertEquals(powerFromDao, power);
    }

    /**
     * Test of deleteSuperPowerById method, of class SuperPowerDaoDB.
     */
    @Test
    public void testDeleteSuperPowerById() {
        SuperPower power = new SuperPower();
        power.setSuperPower("test superPower");
        power.setDescription("Super power description");
        power = superPowerDao.addSuperPower(power);
        
        SuperPower powerFromDao = superPowerDao.getSuperPowerById(power.getId());
        assertNotNull(powerFromDao);

        superPowerDao.deleteSuperPowerById(power.getId());
        powerFromDao = superPowerDao.getSuperPowerById(power.getId());
        assertNull(powerFromDao);
    }

}
