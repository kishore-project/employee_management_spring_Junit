package com.ideas2it.employeemanagement.department.service;

import java.util.List;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import org.springframework.stereotype.Component;;

/**
 *<p>
 * Interface for DepartmentService  to handle  department-related operation.
 *</p>
 * @author  Kishore
 */
@Component
public interface DepartmentService {

    /**
     * Adds a new department to the Database.
     *
     * @param departmentDto {@link DepartmentDto} the department entity to be added
     * @return the saved department entity.
     */
    DepartmentDto addDepartment(DepartmentDto departmentDto);

    /**
     * Retrieves all active department.
     *
     * @return a list of active department entities
     */
    List<DepartmentDto> getAllDepartments();

    /**
     * Retrieves an departemnt by their ID.
     *
     * @param id the ID of the department to be retrieved
     * @return the employee entity with the specified ID
     */
    DepartmentDto getDepartmentById(int id);

    /**
     * Updates an existing department.
     *
     * @param departmentdto {@link DepartmentDto} the department entity with updated information
     * @return the updated department entity
     */
    DepartmentDto updateDepartment(int id, DepartmentDto departmentdto);

    /**
     * Soft deletes a department by setting them as inactive.
     *
     * @param id the ID of the sport to be deleted
     */
    void deleteDepartment(int id) ;

    /**
     * Retrieves all active employee By department ID.
     *
     * @return a list of active employee entities
     */
    List<EmployeeDto> getEmployeesByDepartmentId(int departmentId);
}

