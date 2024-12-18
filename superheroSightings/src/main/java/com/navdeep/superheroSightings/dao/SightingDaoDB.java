/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.dao.LocationDaoDB.LocationRowMapper;
import com.navdeep.superheroSightings.entities.Hero;
import com.navdeep.superheroSightings.entities.Location;
import com.navdeep.superheroSightings.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id=?";
    final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
    final String INSERT_SIGHTING = "INSERT INTO sighting(locationId,heroId,date,description) VALUES(?,?,?,?)";
    final String UPDATE_SIGHTING = "UPDATE sighting SET locationId=?,heroId=?,date=?,description=? WHERE id=?";
    final String DELETE_SIGHTING_BY_ID = "DELETE FROM sighting WHERE id=?";
    final String SELECT_SIGHTING_BY_LOCATION = "SELECT * FROM sighting WHERE locationId=?";
    final String SELECT_ALL_LOCATIONS_BY_HERO_ID = "SELECT l.* FROM location l JOIN sighting s ON "
            + "l.id=s.locationId WHERE heroId=?";
    final String SELECT_ALL_SIGHTING_BY_DATE = "SELECT * FROM sighting WHERE DATE(date)=?";
    final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s ON l.id=s.locationId "
            + "WHERE s.id=?";
    final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM hero h JOIN sighting s ON h.id=s.heroId "
            + "WHERE s.id=?";
    final String SELECT_SIGHTINGS_BY_HERO="select * FROM sighting WHERE heroId=?";
    
    @Override
    public Sighting getSighingById(int id) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SELECT_SIGHTING_BY_ID,
                    new SightingRowMapper(), id);
            return sighting;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbcTemplate.query(SELECT_ALL_SIGHTINGS, new SightingRowMapper());
        return sightings;
    }

    @Transactional
    @Override
    public Sighting addSighting(Sighting sighting) {
        jdbcTemplate.update(INSERT_SIGHTING,
                sighting.getLocation().getId(),
                sighting.getHero().getId(),
                Timestamp.valueOf(sighting.getDate()),
                sighting.getDescription());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
//        sighting.setHero(getHeroForSighting(newId));
        sighting.setLocation(getLocationForSighting(newId));
        return sighting;

    }

    @Override
    public void updateSighting(Sighting sighting) {
        jdbcTemplate.update(UPDATE_SIGHTING,
                sighting.getLocation().getId(),
                sighting.getHero().getId(),
                Timestamp.valueOf(sighting.getDate()),
                sighting.getDescription(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        jdbcTemplate.update(DELETE_SIGHTING_BY_ID,
                id);
    }

    @Override
    public List<Sighting> getAllSightingByLocation(Location location) {
        List<Sighting> sightings = jdbcTemplate.query(SELECT_SIGHTING_BY_LOCATION,
                new SightingRowMapper(),
                location.getId());
        return sightings;
    }

    @Override
    public List<Location> getAllLocationsHeroSeen(Hero hero) {
        return jdbcTemplate.query(SELECT_ALL_LOCATIONS_BY_HERO_ID,
                new LocationRowMapper(),
                hero.getId());
    }

    @Override
    public List<Sighting> getAllSightingByDate(LocalDateTime date) {       
        List<Sighting> sightings = jdbcTemplate.query(SELECT_ALL_SIGHTING_BY_DATE,
                new SightingRowMapper(),
                date);
        return sightings;
    }

    private Location getLocationForSighting(int id) {
        return jdbcTemplate.queryForObject(SELECT_LOCATION_FOR_SIGHTING,
                new LocationRowMapper(),
                id);
    }

    @Override
    public List<Sighting> getAllSightingsByHero(Hero hero) {
        return jdbcTemplate.query(SELECT_SIGHTINGS_BY_HERO,
                new SightingRowMapper(),
                hero.getId());
    }

    public final class SightingRowMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getTimestamp("date").toLocalDateTime());
            sighting.setDescription(rs.getString("description"));
            sighting.setHero(heroDao.getHeroById(Integer.parseInt(rs.getString("heroId"))));
            sighting.setLocation(locationDao.getLocationById(Integer.parseInt(rs.getString("locationId"))));
            return sighting;
        }

    }
}
