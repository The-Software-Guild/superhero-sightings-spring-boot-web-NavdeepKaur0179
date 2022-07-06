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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHeroById() {
        Organization organization=new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        
        organization=organizationDao.addOrganization(organization);
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);  
        testHero= heroDao.addHero(testHero);        
        
        Hero fromDao=heroDao.getHeroById(testHero.getId());
        assertEquals(testHero, fromDao);

    }
    

    /**
     * Test of getAllHeros method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeros() {
        Organization organization=new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        
        organization=organizationDao.addOrganization(organization);
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);  
        testHero= heroDao.addHero(testHero);
                     
        Hero testHero2 = new Hero();
        testHero2.setName("test Hero Name 2");
        testHero2.setDescription("Test Hero Description 2");
        testHero2.setSuperPower("test Hero SuperPower 2");  
        testHero2.setOrganizations(organizationList);
        testHero2= heroDao.addHero(testHero2);
        
        List<Hero> heros=heroDao.getAllHeros();
        assertEquals(2, heros.size());
        assertTrue(heros.contains(testHero));
        assertTrue(heros.contains(testHero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
       Organization organization=new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        
        organization=organizationDao.addOrganization(organization);
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);  
        testHero= heroDao.addHero(testHero);
        
        Hero fromDao=heroDao.getHeroById(testHero.getId());
        
        testHero.setName("updated Hero Test Name");
        heroDao.updateHero(testHero);
        
        assertNotEquals(fromDao, testHero);
        
        fromDao=heroDao.getHeroById(testHero.getId());
        assertEquals(fromDao, testHero);
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {
        Organization organization=new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        
        organization=organizationDao.addOrganization(organization);
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);  
        testHero= heroDao.addHero(testHero);
        
        List<Hero> heros=new ArrayList<>();
        heros.add(testHero);
        
        Location location=new Location();
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatlong("Test Location latlong");
        location=locationDao.addLocation(location);        
        
        Sighting sighting=new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setHero(testHero);
        sighting.setLocation(location);
        sighting.setDescription(" Test Sighting description");
        sighting=sightingDao.addSighting(sighting);
        
        Hero fromDao=heroDao.getHeroById(testHero.getId());
        assertNotNull(fromDao);
        
        heroDao.deleteHeroById(testHero.getId());
        fromDao=heroDao.getHeroById(testHero.getId());
        assertNull(fromDao);      
        
    }
    
    @Test
    public void testGetAllMemberHerosOfOrganization()
    {
        Organization organization=new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization description");
        organization.setAddress("Test Organization address");
        
        organization=organizationDao.addOrganization(organization);
        
        List<Organization> organizationList=new ArrayList<>();
        organizationList.add(organization);
        
        Hero testHero = new Hero();
        testHero.setName("test Hero Name 1");
        testHero.setDescription("Test Hero Description");
        testHero.setSuperPower("test SuperPower");
        testHero.setOrganizations(organizationList);  
        testHero= heroDao.addHero(testHero);
        
        Hero testHero2 = new Hero();
        testHero2.setName("test Hero Name 2");
        testHero2.setDescription("Test Hero Description 2");
        testHero2.setSuperPower("test Hero SuperPower 2");  
        testHero2.setOrganizations(organizationList);
        testHero2= heroDao.addHero(testHero2);
        
        List<Hero> heros=heroDao.getAllMemberHerosOfOrganization(organization);
        assertEquals(2, heros.size());
        assertTrue(heros.contains(testHero));
        assertTrue(heros.contains(testHero2));
    }
}
