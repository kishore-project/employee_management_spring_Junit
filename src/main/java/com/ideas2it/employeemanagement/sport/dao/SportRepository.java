package com.ideas2it.employeemanagement.sport.dao;

import com.ideas2it.employeemanagement.model.Sport;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing Sport entities.
 * Provides CRUD operations for Sport entities.
 * This interface extends CrudRepository to inherit basic CRUD methods.
 */
public interface SportRepository extends CrudRepository<Sport, Integer> {

    boolean existsByName(String name);
}
