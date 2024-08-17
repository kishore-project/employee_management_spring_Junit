package com.ideas2it.employeemanagement.department.dao;

import com.ideas2it.employeemanagement.model.Department;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Department entities.
 * Provides CRUD operations for Department entities.
 * This interface extends CrudRepository to inherit basic CRUD methods.
 */
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    boolean existsByName(String name);
}
