package com.ideas2it.employeemanagement.employee.controller;

import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.sport.service.SportService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Employee entities.
 * Provides endpoints to create, retrieve, update, delete employees,
 * and manage their associated department & sports.
 */
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SportService sportService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * Creates a new employee.
     *
     * @param employeeDto {@link EmployeeDto} The DTO containing employee data.
     * @return The created employee DTO with HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("Request to create employee with details: {}", employeeDto);
            EmployeeDto createdEmployeeDto = employeeService.addEmployee(employeeDto);
            logger.info("Employee created with ID: {}", createdEmployeeDto.getId());
            return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all active employees.
     *
     * @return A list of employee DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        logger.info("Request to retrieve all employees");
            List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();
            logger.info("Retrieved {} employees", employeeDtos.size());
            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id The ID of the employee.
     * @return The employee DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@Valid @PathVariable int id) {
        logger.info("Request to retrieve employee with ID: {}", id);
            EmployeeDto employeeDto = employeeService.getEmployeeById(id);
            logger.info("Retrieved employee with ID: {}", id);
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * Updates an existing employee.
     *
     * @param id The ID of the employee to be updated.
     * @param employeeDto {@link EmployeeDto} The DTO containing updated employee data.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @PathVariable int id, @RequestBody EmployeeDto employeeDto) {
        logger.info("Request to update employee with ID: {}", id);
            EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(id, employeeDto);
            logger.info("Updated employee with ID: {}", id);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to be deleted.
     * @return HTTP status 204 No Content.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        logger.info("Request to delete employee with ID: {}", id);
            employeeService.deleteEmployee(id);
            logger.info("Employee with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Adds a sport to an employee's list of sports.
     *
     * @param employeeId The ID of the employee.
     * @param sportId The ID of the sport to be added.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/{employeeId}/addSport/{sportId}")
    public ResponseEntity<EmployeeDto> addSportToEmployee(@PathVariable int employeeId, @PathVariable int sportId) {
        logger.info("Request to add sport with ID: {} to employee with ID: {}", sportId, employeeId);
            EmployeeDto updatedEmployeeDto = employeeService.addSportToEmployee(employeeId, sportId);
            logger.info("Added sport with ID: {} to employee with ID: {}", sportId, employeeId);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
    }

    /**
     * Removes a sport from an employee's list of sports.
     *
     * @param employeeId The ID of the employee.
     * @param sportId The ID of the sport to be removed.
     * @return The updated employee DTO with HTTP status 200 OK.
     */
    @PutMapping("/{employeeId}/removeSport/{sportId}")
    public ResponseEntity<EmployeeDto> removeSportFromEmployee(@PathVariable int employeeId, @PathVariable int sportId) {
        logger.info("Request to remove sport with ID: {} from employee with ID: {}", sportId, employeeId);
            EmployeeDto updatedEmployeeDto = employeeService.removeSportFromEmployee(employeeId, sportId);
            logger.info("Removed sport with ID: {} from employee with ID: {}", sportId, employeeId);
            return new ResponseEntity<>(updatedEmployeeDto, HttpStatus.OK);
    }
}
