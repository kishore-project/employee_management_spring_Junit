package com.ideas2it.employeemanagement.employee.mapper;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.utilities.Validator;

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
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setDob(employee.getDob());
        employeeDto.setEmailId(employee.getEmailId());
        employeeDto.setDepartmentID(employee.getDepartment().getId());
        employeeDto.setDepartmentName(employee.getDepartment().getName());
        employeeDto.setStreet(employee.getAddress().getStreet());
        employeeDto.setCity(employee.getAddress().getCity());
        employeeDto.setState(employee.getAddress().getState());
        employeeDto.setZip(employee.getAddress().getZip());

        // Convert the set of sport entities to a set of SportDto
        employeeDto.setSports(employee.getSports().stream()
                .map(sport -> new SportDto(sport.getId(), sport.getName()))
                .collect(Collectors.toSet()));
        employeeDto.setAge(Validator.calculateAge(employee.getDob()));

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
