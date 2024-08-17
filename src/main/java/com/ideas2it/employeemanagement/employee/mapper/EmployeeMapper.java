package com.ideas2it.employeemanagement.employee.mapper;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.utilities.Validator;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * A utility class for mapping between Employee and EmployeeDto objects,
 */
public class EmployeeMapper {

    /**
     * Converts an Employee entity to an EmployeeDto.
     *
     * @param employee The Employee entity to be converted.
     * @return The corresponding EmployeeDto.
     */
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dob(employee.getDob())
                .emailId(employee.getEmailId())
                .departmentID(employee.getDepartment() != null ? employee.getDepartment().getId() : 0)
                .departmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                .street(employee.getAddress() != null ? employee.getAddress().getStreet() : null)
                .city(employee.getAddress() != null ? employee.getAddress().getCity() : null)
                .state(employee.getAddress() != null ? employee.getAddress().getState() : null)
                .zip(employee.getAddress() != null ? employee.getAddress().getZip() : null)
                .sports(employee.getSports() != null ? employee.getSports().stream()
                        .map(sport -> SportDto.builder()
                                .id(sport.getId())
                                .name(sport.getName())
                                .build())
                        .collect(Collectors.toSet()) : Collections.emptySet())
                .age(Validator.calculateAge(employee.getDob()))
                .build();

        return employeeDto;
    }

    /**
     * Converts an EmployeeDto to an Employee entity.
     *
     * @param employeeDto The EmployeeDto to be converted.
     * @return The corresponding Employee entity.
     */
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDob(employeeDto.getDob());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setActive(true); // Default to active when adding.
        return employee;
    }

}
