package com.ideas2it.employeemanagement.department.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.dao.DepartmentRepository;
import com.ideas2it.employeemanagement.department.mapper.DepartmentMapper;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Implement if DepartmentService interface to handle department-related operations.
 * </p>
 * @author  Kishore
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByName(departmentDto.getName())) {
            logger.error("Department already exists with name: {}", departmentDto.getName());
            throw new ResourceAlreadyExistsException("Department already exists with name: " + departmentDto.getName());
        }
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department createdDepartment = departmentRepository.save(department);
        logger.info("Adding department with name: {}",departmentDto.getName());
        return DepartmentMapper.mapToDepartmentDto(createdDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        Iterable<Department> allDepartments = departmentRepository.findAll();
        for (Department department : allDepartments) {
            if (!department.isDeleted()) {
                departments.add(department);
            }
        }
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentDtos.add(DepartmentMapper.mapToDepartmentDto(department));
        }
        logger.info("Retrieving list of all departments");
        return departmentDtos;
    }

    @Override
    public DepartmentDto getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
        if (department.isDeleted()) {
            logger.error("Department is deleted with ID: " + id);
            throw new ResourceNotFoundException("Department is deleted with ID: " + id);
        }
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(int id, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
        if (existingDepartment.isDeleted()) {
            logger.error("Cannot update a deleted department with ID: " + id);
            throw new ResourceNotFoundException("Cannot update a deleted department with ID: " + id);
        }
        existingDepartment.setName(departmentDto.getName());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        logger.info("Updated department with name {}",departmentDto.getName());
        return DepartmentMapper.mapToDepartmentDto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(int id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
        existingDepartment.setDeleted(true);
        departmentRepository.save(existingDepartment);
        logger.info("Department is Deleted {}",existingDepartment.getName());
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentId(int departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        List<Employee> activeEmployees = new ArrayList<>();
        for (Employee employee : department.getEmployees()) {
            if (employee.isActive()) {
                activeEmployees.add(employee);
            }
        }
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : activeEmployees) {
            employeeDtos.add(EmployeeMapper.mapToEmployeeDto(employee));
        }
        logger.info("Retrieving list of Employee in Department{}",employeeDtos.size());
        return employeeDtos;
    }
}