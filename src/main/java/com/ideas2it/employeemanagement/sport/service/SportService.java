package com.ideas2it.employeemanagement.sport.service;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *<p>
 * Interface for SportService  to handle  sport-related operation.
 *</p>
 * @author  Kishore
 */
@Service
public interface SportService {

    /**
     * Adds a new sport to the Database.
     *
     * @param sportDto {@link SportDto} the sport entity to be added
     * @return the saved sport entity.
     */
    SportDto addSport(SportDto sportDto);

    /**
     * Retrieves all active sport.
     *
     * @return a list of active sport entities
     */
    List<SportDto> getAllSports();

    /**
     * Retrieves a sport by their ID.
     *
     * @param id the ID of the sport to be retrieved
     * @return the sport entity with the specified ID
     */
    SportDto getSportById(int id);

    /**
     * Updates an existing sport.
     *
     * @param sportDto {@link SportDto} the sport entity with updated information
     * @return the updated sport entity
     */
    SportDto updateSport(int id, SportDto sportDto);

    /**
     * Soft deletes a sport by setting them as inactive.
     *
     * @param id the ID of the sport to be deleted
     */
    void deleteSport(int id);
    /**
     * Retrieves all active employee By sport ID.
     *
     * @return a list of active employee entities
     */
    List<EmployeeDto> getEmployeesBySportId(int sportId);
}
