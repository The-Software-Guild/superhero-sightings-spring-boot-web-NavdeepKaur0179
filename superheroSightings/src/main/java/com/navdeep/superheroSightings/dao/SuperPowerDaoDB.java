/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.SuperPower;
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
 * @author kaurn
 */
@Repository
public class SuperPowerDaoDB implements SuperPowerDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String SELECT_SUPER_POWER_BY_ID = "SELECT * FROM superPower WHERE id=?";
    final String SELECT_ALL_SUPER_POWERS = "SELECT * FROM superPower";
    final String INSERT_SUPER_POWERS = "INSERT INTO superPower(superPower,description) VALUES(?,?)";
    final String UPDATE_SUPER_POWERS = "UPDATE superPower SET superPower=?,description=? WHERE id=?";
    final String DELETE_SUPER_POWERS_BY_ID = "DELETE FROM superPower WHERE id=?";
    final String SELECT_SUPER_POWER_OF_HERO="SELECT s.* FROM superPower s JOIN hero h ON s.id=h.superPowerId WHERE h.id=?";

    @Override
    public SuperPower getSuperPowerById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_SUPER_POWER_BY_ID,
                    new SuperPowerRowMapper(),
                    id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return jdbcTemplate.query(SELECT_ALL_SUPER_POWERS, new SuperPowerRowMapper());
    }

    @Transactional
    @Override
    public SuperPower addSuperPower(SuperPower superPower) {
        jdbcTemplate.update(INSERT_SUPER_POWERS,
                superPower.getSuperPower(),
                superPower.getDescription());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setId(newId);
        return superPower;
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        jdbcTemplate.update(UPDATE_SUPER_POWERS,
                superPower.getSuperPower(),
                superPower.getDescription(),
                superPower.getId());
    }

    @Override
    public void deleteSuperPowerById(int id) {
        jdbcTemplate.update(DELETE_SUPER_POWERS_BY_ID, id);
    }

    @Override
    public SuperPower getSuperPowerOfHero(Hero hero) {
        return jdbcTemplate.queryForObject(SELECT_SUPER_POWER_OF_HERO, 
                new SuperPowerRowMapper(),
                hero.getId());
    }

    public static class SuperPowerRowMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int rowNum) throws SQLException {
            SuperPower superPower = new SuperPower();
            superPower.setId(rs.getInt("id"));
            superPower.setSuperPower(rs.getString("superPower"));
            superPower.setDescription(rs.getString("description"));
            return superPower;
        }
    }

}
