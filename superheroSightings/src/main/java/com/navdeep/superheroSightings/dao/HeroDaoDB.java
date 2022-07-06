/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.dao.OrganizationDaoDB.OrganizationRowMapper;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE id=?";
    final String SELECT_ALL_HEROS = "SELECT * FROM hero";
    final String INSERT_HERO = "INSERT INTO hero(name,description,superPower) VALUES(?,?,?)";
    final String UPDATE_HERO = "UPDATE hero SET name=?,description=?,superPower=? WHERE id=?";
    final String DELETE_HERO_BY_ID = "DELETE FROM hero WHERE id=?";
    final String DELETE_HERO_ORGANIZATION_BY_HERO_ID = "DELETE FROM hero_organization WHERE heroID=?";
    final String DELETE_SIGHTING_BY_HERO_BY_ID = "DELETE FROM sighting WHERE heroId=?";
    final String SELECT_ALL_ORGANIZATIONS_BY_HERO_ID = "SELECT o.* FROM organization o JOIN hero_organization ho "
            + "ON o.id=ho.organizationId WHERE heroId=?";
    final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_organization(heroId,organizationId) "
            + "VALUES(?,?)";
    final String SELECT_HEROS_FOR_ORGANIZATION_BY_ID = "SELECT h.* FROM hero h JOIN hero_organization ho "
            + "ON h.id=ho.heroId WHERE organizationId=?";

    @Override
    public Hero getHeroById(int id) {
        try {
            Hero hero = jdbcTemplate.queryForObject(SELECT_HERO_BY_ID,
                    new HeroRowMapper(),
                    id);
            List<Organization> organizations = getOrganizationsOfHero(hero);
            hero.setOrganizations(organizations);
            return hero;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeros() {
        List<Hero> heros = jdbcTemplate.query(SELECT_ALL_HEROS,
                new HeroRowMapper());
        for (Hero hero : heros) {
            List<Organization> organizations = getOrganizationsOfHero(hero);
            hero.setOrganizations(organizations);
        }
        return heros;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        jdbcTemplate.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower());
        int newId = jdbcTemplate.queryForObject("SELECT Last_INSERT_ID()", Integer.class);
        hero.setId(newId);
        insertOrganizationsForHero(hero);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        jdbcTemplate.update(DELETE_HERO_ORGANIZATION_BY_HERO_ID, hero.getId());
        insertOrganizationsForHero(hero);
        jdbcTemplate.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower(),
                hero.getId());
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        jdbcTemplate.update(DELETE_HERO_ORGANIZATION_BY_HERO_ID, id);
        jdbcTemplate.update(DELETE_SIGHTING_BY_HERO_BY_ID, id);
        jdbcTemplate.update(DELETE_HERO_BY_ID, id);
    }

    private List<Organization> getOrganizationsOfHero(Hero hero) {
        List<Organization> organizationList = jdbcTemplate.query(SELECT_ALL_ORGANIZATIONS_BY_HERO_ID,
                new OrganizationRowMapper(), hero.getId());
        return organizationList;
    }

    private void insertOrganizationsForHero(Hero hero) {
        for (Organization organization : hero.getOrganizations()) {
            jdbcTemplate.update(INSERT_HERO_ORGANIZATION,
                    hero.getId(), organization.getId());
        }
    }

    @Override
    public List<Hero> getAllMemberHerosOfOrganization(Organization organization) {
        List<Hero> heros=jdbcTemplate.query(SELECT_HEROS_FOR_ORGANIZATION_BY_ID,
                new HeroRowMapper(),organization.getId());
        for (Hero hero : heros) {
            List<Organization> organizations=getOrganizationsOfHero(hero);
            hero.setOrganizations(organizations);
        }
        return heros;
    }

    public static final class HeroRowMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperPower(rs.getString("superPower"));
            return hero;
        }

    }
}
