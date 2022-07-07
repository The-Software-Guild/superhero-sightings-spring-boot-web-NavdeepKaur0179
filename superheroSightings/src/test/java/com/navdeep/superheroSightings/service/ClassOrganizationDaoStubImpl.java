/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.dao.OrganizationDao;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaurn
 */
public class ClassOrganizationDaoStubImpl implements OrganizationDao{
    
    private Organization onlyOrganization;
    
    public ClassOrganizationDaoStubImpl()
    {
        onlyOrganization=new Organization();
        onlyOrganization.setId(1);
        onlyOrganization.setName("Test Organization Name");
        onlyOrganization.setDescription("Test Organization description");
        onlyOrganization.setAddress("Test Organization address");
    }

    public ClassOrganizationDaoStubImpl(Organization onlyOrganization) {
        this.onlyOrganization = onlyOrganization;
    }
    
    @Override
    public Organization getOrganizationById(int id) {
        if(id == onlyOrganization.getId())
        {
           return onlyOrganization;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
      List<Organization> organizations=new ArrayList<>();
      organizations.add(onlyOrganization);
      return organizations;
    }

    @Override
    public Organization addOrganization(Organization organizationToAdd) {
        if(organizationToAdd.equals(onlyOrganization))
        {
            return onlyOrganization;
        }
        else{
            return null;
        }
    }

    @Override
    public void updateorganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteOrganizationbyId(int id) {
        onlyOrganization=null;
    }

    @Override
    public List<Organization> getAllOrganizationsOfHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
