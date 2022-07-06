/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.dao;

import com.navdeep.superheroSightings.entities.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id=?";
    final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
    final String INSERT_LOCATION = "INSERT INTO location(name,description,address,latlong) VALUES(?,?,?,?)";
    final String UPDATE_LOCATION = "UPDATE location SET name=?,description=?,address=?,latlong=? WHERE id=?";
    final String DELETE_LOCATION_BY_ID = "DELETE FROM location WHERE id=?";
    final String DELETE_SIGHTING_OF_LOCATION_BY_LOCATION_ID = "DELETE FROM sighting WHERE locationId=?";

    @Override
    public Location getLocationById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_LOCATION_BY_ID,
                    new LocationRowMapper(),
                    id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SELECT_ALL_LOCATIONS, new LocationRowMapper());
    }

    @Transactional
    @Override
    public Location addLocation(Location location) {
        jdbcTemplate.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatlong());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatlong(),
                location.getId());
    }
    
    @Transactional
    @Override
    public void deleteLocationById(int id) {
        jdbcTemplate.update(DELETE_SIGHTING_OF_LOCATION_BY_LOCATION_ID,id);
        jdbcTemplate.update(DELETE_LOCATION_BY_ID,id);        
    }

    public final static class LocationRowMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatlong(rs.getString("latlong"));
            return location;
        }

    }
}
