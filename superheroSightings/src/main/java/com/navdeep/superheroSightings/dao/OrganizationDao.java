/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Organization;
import java.util.List;

/**
 *
 * @author nkaur
 */
public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateorganization(Organization organization);
    void deleteOrganizationbyId(int id);
   
    List<Organization> getAllOrganizationsOfHero(Hero hero);
}
