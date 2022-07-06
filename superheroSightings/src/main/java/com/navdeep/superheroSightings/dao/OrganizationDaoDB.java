/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.dao.HeroDaoDB.HeroRowMapper;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nkaur
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id=?";
    final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
    final String INSERT_ORGANIZATION = "INSERT INTO organization(name,description,address) VALUES(?,?,?)";
    final String UPDATE_ORGANIZATION = "UPDATE organization SET name=?,description=?,address=? WHERE id=?";
    final String DELETE_ORGANIZATION_BY_ID = "DELETE FROM organization WHERE id=?";
    final String DELETE_HERO_ORGANIZATION_BY_ID = "DELETE FROM hero_organization WHERE OrganizationId=?";
    final String SELECT_ORGANIZATIONS_BY_HERO_ID = "SELECT o.* from organization o JOIN hero_organization ho "
            + "ON o.id=ho.organizationId WHERE heroId=?";
    final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_organization(heroId,organizationId) VALUES(?,?)";

    @Override
    public Organization getOrganizationById(int id) {
        try {
            Organization organization = jdbcTemplate.queryForObject(SELECT_ORGANIZATION_BY_ID,
                    new OrganizationRowMapper(),
                    id);
            return organization;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> organizations = jdbcTemplate.query(SELECT_ALL_ORGANIZATIONS,
                new OrganizationRowMapper());        
        return organizations;
    }

    @Transactional
    @Override
    public Organization addOrganization(Organization organization) {
        jdbcTemplate.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress());
        int newId = jdbcTemplate.queryForObject(("SELECT LAST_INSERT_ID()"), Integer.class);
        organization.setId(newId);       
        return organization;
    }

    @Override
    public void updateorganization(Organization organization) {       
        jdbcTemplate.update(DELETE_HERO_ORGANIZATION_BY_ID,organization.getId());       
        jdbcTemplate.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getId());       
    }

    @Transactional
    @Override
    public void deleteOrganizationbyId(int id) {
        jdbcTemplate.update(DELETE_HERO_ORGANIZATION_BY_ID, id);
        jdbcTemplate.update(DELETE_ORGANIZATION_BY_ID, id);
    }

//    @Override
//    public List<Hero> getAllMemberHerosOfOrganization(Organization organization) {
//        List<Hero> heros=  jdbcTemplate.query(SELECT_HEROS_FOR_ORGANIZATION_BY_ID,
//                new HeroRowMapper(),
//                organization.getId());
//        for (Hero hero : heros) {
//            List<Organization> heroOrganzations=getAllOrganizationsOfHero(hero);            
//            hero.setOrganizations(heroOrganzations);
//        }
//        return heros;
//    }

    @Override
    public List<Organization> getAllOrganizationsOfHero(Hero hero) {
        List<Organization> organizations = jdbcTemplate.query(SELECT_ORGANIZATIONS_BY_HERO_ID,
                new OrganizationRowMapper(),
                hero.getId());
        
        return organizations;

    } 
    
    public static class OrganizationRowMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            return organization;
        }
    }
}
