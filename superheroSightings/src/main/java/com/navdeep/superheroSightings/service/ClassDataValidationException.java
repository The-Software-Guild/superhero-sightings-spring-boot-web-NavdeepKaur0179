/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.service;

/**
 *
 * @author kaurn
 */
public class ClassDataValidationException extends Exception {

    public ClassDataValidationException(String message) {
        super(message);
    }

    public ClassDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
