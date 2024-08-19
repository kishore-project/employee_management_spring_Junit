package com.ideas2it.employeemanagement.controller;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.sport.controller.SportController;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SportControllerTest {
    @Mock
    private SportService sportService;

    @InjectMocks
    private SportController sportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSport_ValidSportDto_ReturnsCreated() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();
        when(sportService.addSport(any(SportDto.class))).thenReturn(sportDto);

        ResponseEntity<SportDto> response = sportController.createSport(sportDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sportDto, response.getBody());

        verify(sportService, times(1)).addSport(any(SportDto.class));
    }

    @Test
    void deleteSport_ValidId_ReturnsFound() {
        int sportId = 1;
        ResponseEntity<Void> response = sportController.deleteSport(sportId);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());

        verify(sportService, times(1)).deleteSport(sportId);
    }

    @Test
    void getAllSports_ReturnsListOfSports() {
        List<SportDto> sportDtos = Arrays.asList(
                SportDto.builder().id(1).name("Basketball").build(),
                SportDto.builder().id(2).name("Football").build()
        );
        when(sportService.getAllSports()).thenReturn(sportDtos);

        ResponseEntity<List<SportDto>> response = sportController.getAllSports();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sportDtos, response.getBody());

        verify(sportService, times(1)).getAllSports();
    }

    @Test
    void getSportById_ValidId_ReturnsSportDto() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();
        when(sportService.getSportById(1)).thenReturn(sportDto);

        ResponseEntity<SportDto> response = sportController.getSportById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sportDto, response.getBody());

        verify(sportService, times(1)).getSportById(1);
    }

    @Test
    void updateSport_ValidId_ReturnsUpdatedSport() {
        SportDto updatedSportDto = SportDto.builder().id(1).name("Basketball").build();
        when(sportService.updateSport(eq(1), any(SportDto.class))).thenReturn(updatedSportDto);

        ResponseEntity<SportDto> response = sportController.updateSport(1, updatedSportDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSportDto, response.getBody());

        verify(sportService, times(1)).updateSport(eq(1), any(SportDto.class));
    }

    @Test
    void getEmployeesBySportId_ValidSportId_ReturnsEmployeeList() {
        List<EmployeeDto> employeeDtos = Arrays.asList(
                EmployeeDto.builder().id(1).name("John Doe").build(),
                EmployeeDto.builder().id(2).name("Jane Smith").build()
        );
        when(sportService.getEmployeesBySportId(1)).thenReturn(employeeDtos);

        ResponseEntity<List<EmployeeDto>> response = sportController.getEmployeesBySportId(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDtos, response.getBody());

        verify(sportService, times(1)).getEmployeesBySportId(1);
    }
}
