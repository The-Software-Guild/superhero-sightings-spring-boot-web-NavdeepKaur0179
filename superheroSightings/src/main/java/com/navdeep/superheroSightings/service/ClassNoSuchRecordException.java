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
public class ClassNoSuchRecordException extends Exception {

    public ClassNoSuchRecordException(String message) {
        super(message);
    }

    public ClassNoSuchRecordException(String message, Throwable cause) {
        super(message, cause);
    }
   
}
