/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.SuperPower;
import java.util.List;

/**
 *
 * @author kaurn
 */
public interface SuperPowerDao {
    SuperPower getSuperPowerById(int id);
    List<SuperPower> getAllSuperPowers();
    SuperPower addSuperPower(SuperPower superPower);
    void updateSuperPower(SuperPower superPower);
    void deleteSuperPowerById(int id);
    
    SuperPower getSuperPowerOfHero(Hero hero);
}
