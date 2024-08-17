package com.ideas2it.employeemanagement.department.mapper;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.model.Department;

/**
 * A utility class for mapping between department and departmentDto objects,
 */
public class DepartmentMapper {
    /**
     * Converts a department entity to an DepartmentDto.
     *
     * @param department The department entity to be converted.
     * @return The corresponding DepartmentDto.
     */
    public static DepartmentDto mapToDepartmentDto(Department department) {
       // return new DepartmentDto(department.getId(), department.getName());
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    /**
     * Converts an DepartmentDto to an department entity.
     *
     * @param departmentDto The DepartmentDto to be converted.
     * @return The corresponding Department entity.
     */
    public static Department mapToDepartment(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .build();
    }
}
