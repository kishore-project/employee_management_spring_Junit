package com.ideas2it.employeemanagement.employee.dao;

import com.ideas2it.employeemanagement.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Employee entities.
 * Provides CRUD operations for Employee entities.
 * This interface extends CrudRepository to inherit basic CRUD methods.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    boolean existsByNameAndDepartmentId(String name, int departmentID);

}
