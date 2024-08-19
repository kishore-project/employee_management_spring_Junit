package com.ideas2it.employeemanagement.controller;

import com.ideas2it.employeemanagement.department.controller.DepartmentController;
import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DepartmentControllerTest {
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDepartment_ValidDepartmentDto_ReturnsCreated() {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1).name("HR").build();
        when(departmentService.addDepartment(any(DepartmentDto.class))).thenReturn(departmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.createDepartment(departmentDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());

        verify(departmentService).addDepartment(any(DepartmentDto.class));
    }

    @Test
    void deleteDepartment_ValidId_ReturnsNoContent() {
       int departmentId = 1;
        doNothing().when(departmentService).deleteDepartment(departmentId);
        ResponseEntity<Void> response = departmentController.deleteDepartment(departmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(departmentService, times(1)).deleteDepartment(departmentId);
    }

    @Test
    void getAllDepartments_ReturnsListOfDepartments() {
        List<DepartmentDto> departmentDtos = Arrays.asList(
                DepartmentDto.builder().id(1).name("HR").build(),
                DepartmentDto.builder().id(2).name("Finance").build()
        );
        when(departmentService.getAllDepartments()).thenReturn(departmentDtos);

        ResponseEntity<List<DepartmentDto>> response = departmentController.getAllDepartments();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDtos, response.getBody());

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void getDepartmentById_ValidId_ReturnsDepartmentDto() {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1).name("HR").build();
        when(departmentService.getDepartmentById(1)).thenReturn(departmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.getDepartmentById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentDto, response.getBody());

        verify(departmentService, times(1)).getDepartmentById(1);
    }

    @Test
    void updateDepartment_ValidId_ReturnsUpdatedDepartment() {
        DepartmentDto updatedDepartmentDto = DepartmentDto.builder().id(1).name("HR").build();
        when(departmentService.updateDepartment(eq(1), any(DepartmentDto.class))).thenReturn(updatedDepartmentDto);

        ResponseEntity<DepartmentDto> response = departmentController.updateDepartment(1, updatedDepartmentDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDepartmentDto, response.getBody());

        verify(departmentService, times(1)).updateDepartment(eq(1), any(DepartmentDto.class));
    }

    @Test
    void getEmployeesByDepartmentId_ValidDepartmentId_ReturnsEmployeeList() {
        List<EmployeeDto> employeeDtos = Arrays.asList(
                EmployeeDto.builder().id(1).name("John Doe").build(),
                EmployeeDto.builder().id(2).name("Jane Smith").build()
        );
        when(departmentService.getEmployeesByDepartmentId(1)).thenReturn(employeeDtos);

        ResponseEntity<List<EmployeeDto>> response = departmentController.getEmployeesByDepartmentId(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDtos, response.getBody());

        verify(departmentService, times(1)).getEmployeesByDepartmentId(1);
    }
}
