package com.ideas2it.employeemanagement.employee.service;

import java.util.List;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import org.springframework.stereotype.Component;
/**
 *<p>
 * Interface for EmployeeService  to handle  employee-related operation.
 *</p>
 * @author  Kishore
 *
 */
@Component
public interface EmployeeService {

    /**
     * Adds a new employee to the system.
     *
     * @param employeeDto {@link EmployeeDto} the employee entity to be added
     * @return the saved employee entity
     */
    EmployeeDto addEmployee(EmployeeDto employeeDto);

    /**
     * Retrieves all active employees.
     *
     * @return a list of active employee entities
     */
    List<EmployeeDto> getAllEmployees();

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to be retrieved
     * @return the employee entity with the specified ID
     */
    EmployeeDto getEmployeeById(int id) ;

    /**
     * Updates an existing employee.
     *
     * @param employeeDto {@link EmployeeDto} the employee entity with updated information
     * @return the updated employee entity
     */
    EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);

    /**
     * Soft deletes an employee by setting them as inactive.
     *
     * @param id the ID of the employee to be deleted
     */
    void deleteEmployee(int id);

    /**
     * Adds a sport to an employee's list of sports.
     *
     * @param employeeId the ID of the employee
     * @param sportId the sport entity to be added
     * @return the updated employee entity
     */
    EmployeeDto addSportToEmployee(int employeeId, int sportId);

    /**
     * Removes a sport from an employee's list of sports.
     *
     * @param employeeId the ID of the employee
     * @param sportId the sport entity to be removed
     * @return the updated employee entity
     */
    EmployeeDto removeSportFromEmployee(int employeeId, int sportId);
}
