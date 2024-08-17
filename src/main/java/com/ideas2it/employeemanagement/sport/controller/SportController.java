package com.ideas2it.employeemanagement.sport.controller;

import java.util.List;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.service.SportService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Sport entities.
 * Provides endpoints to create, retrieve, update, delete sport,
 * and manage their associated employees.
 */
@RestController
@RequestMapping("api/v1/sports")
public class SportController {
    @Autowired
    private SportService sportService;

    private static final Logger logger = LogManager.getLogger(SportController.class);

    /**
     * Creates a new sport.
     *
     * @param sportDto {@link SportDto} The DTO containing sport data.
     * @return The created sport DTO with HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<SportDto> createSport(@Valid @RequestBody SportDto sportDto) {
        logger.info("Request to create sport with name: {}", sportDto.getName());
            SportDto createdSportDto = sportService.addSport(sportDto);
            logger.info("Sport created with ID: {}", createdSportDto.getId());
            return new ResponseEntity<>(createdSportDto, HttpStatus.CREATED);
    }

    /**
     * Deletes a sport by ID.
     *
     * @param id The ID of the sport to be deleted.
     * @return HTTP status 302 Found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable int id) {
        logger.info("Request to delete sport with ID: {}", id);
            sportService.deleteSport(id);
            logger.info("Sport with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.FOUND);
    }

    /**
     * Retrieves a list of all active sports.
     *
     * @return A list of sport DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<SportDto>> getAllSports() {
        logger.info("Retrieving list of all sports");
            List<SportDto> sportDtos = sportService.getAllSports();
            logger.info("Retrieved {} sports", sportDtos.size());
            return new ResponseEntity<>(sportDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a sport by ID.
     *
     * @param id The ID of the sport.
     * @return The sport DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable int id) {
        logger.info("Request to retrieve sport with ID: {}", id);
            SportDto sportDto = sportService.getSportById(id);
            logger.info("Retrieved sport with ID: {}", id);
            return new ResponseEntity<>(sportDto, HttpStatus.OK);
    }

    /**
     * Updates an existing sport.
     *
     * @param id The ID of the sport to be updated.
     * @param sportDto {@link SportDto}The DTO containing updated sport data.
     * @return The updated sport DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SportDto> updateSport(@Valid @PathVariable int id, @RequestBody SportDto sportDto) {
        logger.info("Request to update sport with ID: {}", id);
            SportDto updatedSportDto = sportService.updateSport(id, sportDto);
            logger.info("Updated sport with ID: {}", id);
            return new ResponseEntity<>(updatedSportDto, HttpStatus.OK);
    }

    /**
     * Gets the employee list by Sport Id.
     *
     * @param sportId The ID of the sport.
     * @return The EmployeeDto list by sport Id with HTTP status 200 OK.
     */
    @GetMapping("/{sportId}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployeesBySportId(@PathVariable int sportId) {
        logger.info("Request to retrieve employees for sport with ID: {}", sportId);
            List<EmployeeDto> employeeDtos = sportService.getEmployeesBySportId(sportId);
            logger.info("Retrieved {} employees for sport with ID: {}", employeeDtos.size(), sportId);
            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}
