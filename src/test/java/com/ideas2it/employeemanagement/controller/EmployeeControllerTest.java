package com.ideas2it.employeemanagement.controller;

import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.controller.EmployeeController;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.service.EmployeeService;
import com.ideas2it.employeemanagement.sport.service.SportService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private SportService sportService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("John");
    }

    @Test
    void testAddEmployee() {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.addEmployee(employeeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeDto> employeeList = Arrays.asList(employeeDto);
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList, response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(eq(1))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(1);
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(eq(1), any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(1, employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).updateEmployee(eq(1), any(EmployeeDto.class));
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployee(eq(1));

        ResponseEntity<Void> response = employeeController.deleteEmployee(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void testAddSportToEmployee() {
        when(employeeService.addSportToEmployee(eq(1), eq(2))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.addSportToEmployee(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).addSportToEmployee(eq(1), eq(2));
    }

    @Test
    void testRemoveSportFromEmployee() {
        when(employeeService.removeSportFromEmployee(eq(1), eq(2))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.removeSportFromEmployee(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());
        verify(employeeService, times(1)).removeSportFromEmployee(eq(1), eq(2));
    }
}
