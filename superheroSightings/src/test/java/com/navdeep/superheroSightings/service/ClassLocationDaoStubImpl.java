/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

import com.navdeep.superheroSightings.dao.LocationDao;
import com.navdeep.superheroSightings.entities.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaurn
 */
public class ClassLocationDaoStubImpl implements LocationDao{

    public Location location;
    
    public ClassLocationDaoStubImpl()
    {
        location=new Location();
        location.setId(1);
        location.setName("Test Location name");
        location.setDescription("Test Location description");
        location.setAddress("Test Location address");
        location.setLatlong("Test Location latlong");
    }

    public ClassLocationDaoStubImpl(Location location) {
        this.location = location;
    }
    
    @Override
    public Location getLocationById(int id) {
        if(id==location.getId())
        {
            return location;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations=new ArrayList<>();
        locations.add(location);
        return locations;
    }

    @Override
    public Location addLocation(Location locationToAdd) {
        if(location.equals(locationToAdd))
        {
            return location;
        }
        else{
            return null;
        }
    }

    @Override
    public void updateLocation(Location updatedlocation) {
//        location.setName(updatedlocation.getName());
//        location.setDescription(updatedlocation.getDescription());
//        location.setAddress(updatedlocation.getAddress());
//        location.setLatlong(updatedlocation.getLatlong());
        
    }

    @Override
    public void deleteLocationById(int id) {
        
    }
    
}
