package com.ideas2it.employeemanagement.department.controller;

import java.util.List;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Department entities.
 * Provides endpoints to create, retrieve, update, delete department,
 * and manage their associated employee.
 */
@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    @Autowired
    private  DepartmentService departmentService;

    private static final Logger logger = LogManager.getLogger(DepartmentController.class);

    /**
     * Creates a new department.
     *
     * @param departmentDto {@link DepartmentDto}The DTO containing department data.
     * @return The created department DTO with HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        logger.info("Creating department with name: {}", departmentDto.getName());
            DepartmentDto createdDepartmentDto = departmentService.addDepartment(departmentDto);
            logger.info("Department created with ID: {}", createdDepartmentDto.getId());
            return new ResponseEntity<>(createdDepartmentDto, HttpStatus.CREATED);
    }

    /**
     * Deletes a department by ID.
     *
     * @param id The ID of the department to be deleted.
     * @return HTTP status 204 No Content.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) {
        logger.info("Deleting department with ID: {}", id);
            departmentService.deleteDepartment(id);
            logger.info("Department with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a list of all active department.
     *
     * @return A list of department DTOs with HTTP status 200 OK.
     */
    @GetMapping("/list")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        logger.info("Retrieving list of all departments");
            List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();
            logger.info("Retrieved {} departments", departmentDtos.size());
            return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a department by ID.
     *
     * @param id The ID of the department.
     * @return The department DTO with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable int id) {
        logger.info("Retrieving department with ID: {}", id);
            DepartmentDto departmentDto = departmentService.getDepartmentById(id);
            logger.info("Retrieved department with ID: {}", id);
            return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    /**
     * Updates an department employee.
     *
     * @param id The ID of the department to be updated.
     * @param departmentDto {@link DepartmentDto} The DTO containing updated department data.
     * @return The updated department DTO with HTTP status 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@Valid @PathVariable int id, @RequestBody DepartmentDto departmentDto) {
        logger.info("Updating department with ID: {}", id);
            DepartmentDto updatedDepartmentDto = departmentService.updateDepartment(id, departmentDto);
            logger.info("Updated department with ID: {}", id);
            return new ResponseEntity<>(updatedDepartmentDto, HttpStatus.OK);
    }

    /**
     * Getting  employee List by DepartmentId.
     *
     * @param departmentId The ID of the department.
     * @return The EmployeeDto List by departmentId with HTTP status 200 OK.
     */
    @GetMapping("/employees/{departmentId}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentId(@PathVariable int departmentId) {
        logger.info("Retrieving employees for department with ID: {}", departmentId);
            List<EmployeeDto> employeeDtos = departmentService.getEmployeesByDepartmentId(departmentId);
            logger.info("Retrieved {} employees for department with ID: {}", employeeDtos.size(), departmentId);
            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }
}