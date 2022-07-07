/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navdeep.superheroSightings.controllers;

import com.navdeep.superheroSightings.service.ClassDataValidationException;
import com.navdeep.superheroSightings.service.ClassEmptyListException;
import com.navdeep.superheroSightings.service.ClassNoSuchRecordException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author kaurn
 */
@ControllerAdvice
@RestController
public class SuperHeroExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CONSTRAINT_MESSAGE="Could not save your item. "
            + "Please ensure it is valid and try again.";
    private static final String NO_MATCHING_RECORD ="No record with this id exists";
    
    
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {
        Error error = new Error();
        error.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Error> handlesqlException(EmptyResultDataAccessException ex,WebRequest request)
    {
        Error error=new Error();
        error.setMessage(NO_MATCHING_RECORD);
        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClassDataValidationException.class)
    public final ResponseEntity<Error> handlesqlException(ClassDataValidationException ex,WebRequest request)
    {
        Error error=new Error();
        error.setMessage(ex.getMessage());
        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClassEmptyListException.class)
    public final ResponseEntity<Error> handlesqlException(ClassEmptyListException ex,WebRequest request)
    {
        Error error=new Error();
        error.setMessage(ex.getMessage());
        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClassNoSuchRecordException.class)
    public final ResponseEntity<Error> handlesqlException(ClassNoSuchRecordException ex,WebRequest request)
    {
        Error error=new Error();
        error.setMessage(ex.getMessage());
        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }
    
}
