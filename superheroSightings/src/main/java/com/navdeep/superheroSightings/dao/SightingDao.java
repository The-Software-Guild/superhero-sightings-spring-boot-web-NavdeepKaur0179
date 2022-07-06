/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nkaur
 */
public interface SightingDao {
    Sighting getSighingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    List<Sighting> getAllSightingByLocation(Location location);
    List<Location> getAllLocationsHeroSeen(Hero hero);
    List<Sighting> getAllSightingByDate(LocalDate date);
    
}
