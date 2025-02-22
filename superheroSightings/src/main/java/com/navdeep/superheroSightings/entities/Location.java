    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.entities;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author nkaur
 */
public class Location {
    private int id;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message="Name must be less than 30 characters.")
    private String name;
    
    @Size(max = 255, message="Description must be less than 255 characters.")
    private String description;
    
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 150, message="Description must be less than 150 characters.")
    private String address;
    
    @NotBlank(message = "Latitude must not be empty.")
    @Size(max = 50, message="Latitude must be less than 50 characters.")
    private String latitude;
    
    @NotBlank(message = "Longitude must not be empty.")
    @Size(max = 50, message="Longitude Longitude must be less than 50 characters.")
    private String longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.description);
        hash = 83 * hash + Objects.hashCode(this.address);
        hash = 83 * hash + Objects.hashCode(this.latitude);
        hash = 83 * hash + Objects.hashCode(this.longitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    
}
