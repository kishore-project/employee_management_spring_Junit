package com.ideas2it.employeemanagement.service;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Department;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dao.SportRepository;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import com.ideas2it.employeemanagement.sport.service.SportServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SportServiceImplTest {
    @Mock
    private SportRepository sportRepository;

    @Spy
    @InjectMocks
    private SportServiceImpl sportService;

    private SportDto sportDto;
    private Sport sport;

    @BeforeEach
    void setUp() {
        sportDto = SportDto.builder()
                .id(1)
                .name("Cricket")
                .build();

        sport = Sport.builder()
                .id(1)
                .name("Cricket")
                .isActive(true)
                .build();
    }

    @Test
    void addSport_ValidSport_ReturnsSportDto() {
        when(sportRepository.existsByName(sportDto.getName())).thenReturn(false);
        when(sportRepository.save(any(Sport.class))).thenReturn(sport);

        SportDto createdSport = sportService.addSport(sportDto);

        assertNotNull(createdSport);
        assertEquals(sportDto.getName(), createdSport.getName());
    }

    @Test
    void addSport_SportAlreadyExists_ThrowsException() {
        when(sportRepository.existsByName(sportDto.getName())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> sportService.addSport(sportDto));

    }

    @Test
    void getSportById_ValidId_ReturnsSportDto() {
        when(sportRepository.findById(1)).thenReturn(Optional.of(sport));

        SportDto foundSport = sportService.getSportById(1);

        assertNotNull(foundSport);
        assertEquals(sportDto.getName(), foundSport.getName());
    }

    @Test
    void getSportById_InvalidId_ThrowsException() {
        when(sportRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sportService.getSportById(1));

    }

    @Test
    void deleteSport_ValidId_DeletesSport() {
        when(sportRepository.findById(1)).thenReturn(Optional.of(sport));

        sportService.deleteSport(1);

        assertFalse(sport.isActive());
        verify(sportRepository).save(sport);

    }

    @Test
    void deleteSport_InvalidId_ThrowsException() {
        when(sportRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sportService.deleteSport(1));

    }

    @Test
    void deleteSport_AlreadyDeleted_ThrowsException() {
        Sport inactiveSport = Sport.builder()
                .id(1)
                .name("Basketball")
                .isActive(false)
                .build();

        when(sportRepository.findById(1)).thenReturn(Optional.of(inactiveSport));

        assertThrows(IllegalStateException.class, () -> sportService.deleteSport(1));

    }

    @Test
    void updateSport_SuccessfulUpdate_ReturnsUpdatedSportDto() {
        SportDto updatedSportDto = SportDto.builder()
                .id(1)
                .name("Football")
                .build();

        when(sportRepository.findById(1)).thenReturn(Optional.of(sport));
        when(sportRepository.save(any(Sport.class))).thenAnswer(invocation -> {
            Sport updatedSport = invocation.getArgument(0);
            updatedSport.setName(updatedSportDto.getName());
            return updatedSport;
        });

        SportDto result = sportService.updateSport(1, updatedSportDto);

        assertNotNull(result);
        assertEquals(updatedSportDto.getName(), result.getName());
    }

    @Test
    void updateSport_NonExistingId_ThrowsException() {
        SportDto updatedSportDto = SportDto.builder()
                .id(2)
                .name("Hockey")
                .build();

        when(sportRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sportService.updateSport(2, updatedSportDto));

    }

    @Test
    void testGetAllSports() {
        Sport activeSport1 = Sport.builder()
                .id(1)
                .name("Cricket")
                .isActive(true)
                .build();

        Sport activeSport2 = Sport.builder()
                .id(2)
                .name("Chess")
                .isActive(true)
                .build();

        Sport inactiveSport = Sport.builder()
                .id(3)
                .name("Football")
                .isActive(false)
                .build();

        List<Sport> sports = Arrays.asList(activeSport1, activeSport2, inactiveSport);

        when(sportRepository.findAll()).thenReturn(sports);

        List<SportDto> sportDtos = sportService.getAllSports();
        assertEquals(2, sportDtos.size());

        SportDto expectedSportDto1 = SportMapper.mapToSportDto(activeSport1);
        SportDto actualSportDto1 = sportDtos.get(0);

        SportDto expectedSportDto2 = SportMapper.mapToSportDto(activeSport2);
        SportDto actualSportDto2 = sportDtos.get(1);

        assertEquals(expectedSportDto1.getId(), actualSportDto1.getId());
        assertEquals(expectedSportDto1.getName(), actualSportDto1.getName());

        assertEquals(expectedSportDto2.getId(), actualSportDto2.getId());
        assertEquals(expectedSportDto2.getName(), actualSportDto2.getName());

    }
    @Test
    void getEmployeesBySportId_ValidSportId_ReturnsActiveEmployeeDtos() {
        Address address1 = Address.builder()
                .street("123 ashok Street")
                .city("chennai")
                .state("Tamil Nadu")
                .zip("627012")
                .build();

        Address address2 = Address.builder()
                .street("456 NGO Avenue")
                .city("chennai")
                .state("Tamil Nadu")
                .zip("627022")
                .build();
        Department department1 = Department.builder()
                .id(1)
                .name("IT")
                .build();

        Department department2 = Department.builder()
                .id(2)
                .name("HR")
                .build();

        Employee activeEmployee1 = Employee.builder()
                .id(1)
                .name("kishore ")
                .dob(LocalDate.of(1990, 1, 1))
                .emailId("kishore@gmail.com")
                .isActive(true)
                .address(address1)
                .department(department1)
                .build();

        Employee activeEmployee2 = Employee.builder()
                .id(2)
                .name("Lefin")
                .dob(LocalDate.of(1992, 2, 2))
                .emailId("Lefin@example.com")
                .isActive(true)
                .address(address2)
                .department(department2)
                .build();

        Employee inactiveEmployee = Employee.builder()
                .id(3)
                .name("Pari")
                .dob(LocalDate.of(1985, 3, 3))
                .emailId("Pari@gmail.com")
                .isActive(false)
                .address(address1)
                .department(department1)
                .build();


        Set<Employee> employees = new HashSet<>(Arrays.asList(activeEmployee1, activeEmployee2, inactiveEmployee));
        sport.setEmployees(employees);

        when(sportRepository.findById(sport.getId())).thenReturn(Optional.of(sport));

        List<EmployeeDto> employeeDtos = sportService.getEmployeesBySportId(sport.getId());

        assertEquals(2, employeeDtos.size());

        EmployeeDto expectedEmployeeDto1 = EmployeeMapper.mapToEmployeeDto(activeEmployee1);
        EmployeeDto expectedEmployeeDto2 = EmployeeMapper.mapToEmployeeDto(activeEmployee2);

        assertTrue(employeeDtos.stream().anyMatch(dto -> dto.getId() == expectedEmployeeDto1.getId()));
        assertTrue(employeeDtos.stream().anyMatch(dto -> dto.getId() == expectedEmployeeDto2.getId()));
    }

}
