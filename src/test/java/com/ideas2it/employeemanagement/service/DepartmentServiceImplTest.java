package com.ideas2it.employeemanagement.service;

import com.ideas2it.employeemanagement.department.dao.DepartmentRepository;
import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.mapper.DepartmentMapper;
import com.ideas2it.employeemanagement.department.service.DepartmentServiceImpl;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    private Department department;
    private DepartmentDto departmentDto;
    private Employee employee;


    @BeforeEach
    void setUp() {
        department = Department.builder()
                .id(1)
                .name("Engineering")
                .build();

        departmentDto = DepartmentDto.builder()
                .id(1)
                .name("Engineering")
                .build();

        employee = Employee.builder()
                .id(1)
                .name("Parri")
                .isActive(true)
                .build();
    }

    @Test
    void addDepartment_ValidDepartment_ReturnsSportDto() {
        when(departmentRepository.existsByName(departmentDto.getName())).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDto createdDepartment = departmentServiceImpl.addDepartment(departmentDto);
        assertNotNull(createdDepartment);
        assertEquals(departmentDto.getName(), createdDepartment.getName());
    }

    @Test
    void addDepartment_DepartmentAlreadyExists_ThrowsException() {
        when(departmentRepository.existsByName(departmentDto.getName())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> departmentServiceImpl.addDepartment(departmentDto));
    }

    @Test
    void getAllDepartments_ReturnsListOfDepartmentDtos() {
        Department activeDepartment1 = Department.builder()
                .id(1)
                .name("HR")
                .build();

        Department activeDepartment2 = Department.builder()
                .id(2)
                .name("IT")
                .build();

        Department inactiveDepartment = Department.builder()
                .id(3)
                .name("Non-IT")
                .isDeleted(true)
                .build();

        List<Department> departments = Arrays.asList(activeDepartment1, activeDepartment2, inactiveDepartment);
        when(departmentRepository.findAll()).thenReturn(departments);
        List<DepartmentDto> departmentDtos = departmentServiceImpl.getAllDepartments();
        assertEquals(2, departmentDtos.size());
        DepartmentDto expectedDepartmentDto1 = DepartmentMapper.mapToDepartmentDto(activeDepartment1);
        DepartmentDto actualDepartmentDto1 = departmentDtos.get(0);
        DepartmentDto expectedDepartmentDto2 = DepartmentMapper.mapToDepartmentDto(activeDepartment2);
        DepartmentDto actualDepartmentDto2 = departmentDtos.get(1);
        assertEquals(expectedDepartmentDto1.getId(), actualDepartmentDto1.getId());
        assertEquals(expectedDepartmentDto1.getName(), actualDepartmentDto1.getName());
        assertEquals(expectedDepartmentDto2.getId(), actualDepartmentDto2.getId());
        assertEquals(expectedDepartmentDto2.getName(), actualDepartmentDto2.getName());
    }

    @Test
    void getDepartmentById_ValidId_ReturnsDepartmentDto() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        DepartmentDto foundDepartment = departmentServiceImpl.getDepartmentById(1);
        assertNotNull(foundDepartment);
        assertEquals(departmentDto.getName(), foundDepartment.getName());
    }

    @Test
    void getDepartmentById_InvalidId_ThrowsException() {
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.getDepartmentById(1));
    }

    @Test
    void deleteDepartment_ValidId_DeletesDepartment() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        departmentServiceImpl.deleteDepartment(1);
        assertTrue(department.isDeleted());
        verify(departmentRepository).save(department);
    }

    @Test
    void deleteDepartment_InvalidId_ThrowsException() {
        when(departmentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.deleteDepartment(1));
    }

    @Test
    void deleteSport_AlreadyDeleted_ThrowsException() {
        Department inActiveDepartment = Department.builder()
                .id(1)
                .name("Basketball")
                .isDeleted(true)
                .build();

        when(departmentRepository.findById(1)).thenReturn(Optional.of(inActiveDepartment));
        assertThrows(IllegalStateException.class, () -> departmentServiceImpl.deleteDepartment(1));
    }

    @Test
    void updateDepartment_SuccessfulUpdate_ReturnsUpdatedDepartmentDto() {
        DepartmentDto updatedDepartmentDto = DepartmentDto.builder()
                .id(1)
                .name("Finance")
                .build();

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenAnswer(invocation -> {
            Department updatedDepartment = invocation.getArgument(0);
            updatedDepartment.setName(updatedDepartmentDto.getName());
            return updatedDepartment;
        });
        DepartmentDto result = departmentServiceImpl.updateDepartment(1, updatedDepartmentDto);
        assertNotNull(result);
        assertEquals(updatedDepartmentDto.getName(), result.getName());
    }

    @Test
    void updateSport_NonExistingId_ThrowsException() {
        DepartmentDto updatedDepartmentDto = DepartmentDto.builder()
                .id(2)
                .name("IT")
                .build();

        when(departmentRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.updateDepartment(2, updatedDepartmentDto));
    }

    @Test
    void getEmployeesByDepartmentId_ValidId_ReturnsEmployeeDtos() {

        Department department = Department.builder()
                .id(1)
                .name("IT")
                .build();

        Employee activeEmployee = Employee.builder()
                .id(1)
                .name("kishore")
                .isActive(true)
                .dob(LocalDate.of(1992, 2, 2))
                .department(department)
                .build();

        Employee inactiveEmployee = Employee.builder()
                .id(2)
                .name("John")
                .dob(LocalDate.of(1992, 2, 2))
                .isActive(false)
                .department(department)
                .build();

        department.setEmployees(new HashSet<>(Arrays.asList(activeEmployee, inactiveEmployee)));
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        List<EmployeeDto> employeeDtos = departmentServiceImpl.getEmployeesByDepartmentId(department.getId());
        assertNotNull(employeeDtos);
        assertEquals(1, employeeDtos.size());
        assertEquals(activeEmployee.getId(), employeeDtos.get(0).getId());
        assertEquals(department.getId(), employeeDtos.get(0).getDepartmentID());
    }

    @Test
    void getEmployeesByDepartmentId_DepartmentNotFound_ThrowsException() {
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.getEmployeesByDepartmentId(department.getId()));
    }
}
