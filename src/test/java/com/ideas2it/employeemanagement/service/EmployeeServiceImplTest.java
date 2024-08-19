package com.ideas2it.employeemanagement.service;

import com.ideas2it.employeemanagement.department.dto.DepartmentDto;
import com.ideas2it.employeemanagement.department.mapper.DepartmentMapper;
import com.ideas2it.employeemanagement.department.service.DepartmentService;
import com.ideas2it.employeemanagement.employee.dao.EmployeeRepository;
import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.service.EmployeeServiceImpl;
import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import com.ideas2it.employeemanagement.sport.service.SportService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SportService sportService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {

        departmentDto = DepartmentDto.builder()
                .id(1)
                .name("IT")
                .build();

        employeeDto = EmployeeDto.builder()
                .id(1)
                .name("Santhosh")
                .dob(LocalDate.of(1990, 1, 1))
                .emailId("santhosh@example.com")
                .departmentID(departmentDto.getId())
                .street("12th Main St")
                .city("Tambaram")
                .state("Tamil Nadu")
                .zip("123456")
                .build();

        employee = Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .dob(employeeDto.getDob())
                .emailId(employeeDto.getEmailId())
                .isActive(true)
                .department(DepartmentMapper.mapToDepartment(departmentDto))
                .address(Address.builder()
                        .street(employeeDto.getStreet())
                        .city(employeeDto.getCity())
                        .state(employeeDto.getState())
                        .zip(employeeDto.getZip())
                        .build())
                .sports(new HashSet<>())
                .build();

    }

    @Test
    void addEmployee_Success() {
        when(employeeRepository.existsByNameAndDepartmentId(employeeDto.getName(), employeeDto.getDepartmentID()))
                .thenReturn(false);
        when(departmentService.getDepartmentById(employeeDto.getDepartmentID()))
                .thenReturn(departmentDto);
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeDto result = employeeService.addEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getName(), result.getName());
        assertEquals(employeeDto.getDepartmentID(), result.getDepartmentID());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void addEmployee_AlreadyExists() {
        when(employeeRepository.existsByNameAndDepartmentId(employeeDto.getName(), employeeDto.getDepartmentID()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.addEmployee(employeeDto));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getAllEmployees_ReturnsActiveEmployees() {
        Employee inactiveEmployee = Employee.builder()
                .id(2)
                .name("Dinesh")
                .isActive(false)
                .build();

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, inactiveEmployee));

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals(employeeDto.getName(), result.get(0).getName());
    }

    @Test
    void getEmployeeById_ValidId_ReturnsEmployee() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeService.getEmployeeById(employeeDto.getId());

        assertNotNull(result);
        assertEquals(employeeDto.getName(), result.getName());
    }

    @Test
    void getEmployeeById_InvalidId_ThrowsException() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(employeeDto.getId()));
    }

    @Test
    void getEmployeeById_InactiveEmployee_ThrowsException() {
        employee.setActive(false);
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));

        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmployeeById(employeeDto.getId()));
    }

    @Test
    void updateEmployee_ValidId_ReturnsUpdatedEmployee() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        when(departmentService.getDepartmentById(employeeDto.getDepartmentID())).thenReturn(departmentDto);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployee(employeeDto.getId(), employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getName(), result.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_InvalidId_ThrowsException() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employeeDto.getId(), employeeDto));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_InactiveEmployee_ThrowsException() {
        employee.setActive(false);
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employeeDto.getId(), employeeDto));
    }
    @Test
    void deleteEmployee_ValidId_DeactivatesEmployee() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(employeeDto.getId());

        assertFalse(employee.isActive());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployee_InvalidId_ThrowsException() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(employeeDto.getId()));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void addSportToEmployee_ValidEmployeeAndSport_AddsSport() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();

        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        when(sportService.getSportById(sportDto.getId())).thenReturn(sportDto);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.addSportToEmployee(employeeDto.getId(), sportDto.getId());

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void addSportToEmployee_AlreadyAssigned_ThrowsException() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();
        employee.getSports().add(SportMapper.mapToSport(sportDto));

        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        when(sportService.getSportById(sportDto.getId())).thenReturn(sportDto);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.addSportToEmployee(employeeDto.getId(), sportDto.getId()));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void addSportToEmployee_InvalidEmployee_ThrowsException() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.addSportToEmployee(employeeDto.getId(), 1));

        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void removeSportFromEmployee_ValidEmployeeAndSport_RemovesSport() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();
        employee.getSports().add(SportMapper.mapToSport(sportDto));

        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        when(sportService.getSportById(sportDto.getId())).thenReturn(sportDto);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.removeSportFromEmployee(employeeDto.getId(), sportDto.getId());

        assertNotNull(result);
        assertTrue(employee.getSports().isEmpty());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void removeSportFromEmployee_InvalidEmployee_ThrowsException() {
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.removeSportFromEmployee(employeeDto.getId(), 1));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void removeSportFromEmployee_SportNotAssigned_ThrowsException() {
        SportDto sportDto = SportDto.builder().id(1).name("Basketball").build();

        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        when(sportService.getSportById(sportDto.getId())).thenReturn(sportDto);

        assertThrows(ResourceNotFoundException.class, () -> employeeService.removeSportFromEmployee(employeeDto.getId(), sportDto.getId()));

        verify(employeeRepository, never()).save(any(Employee.class));
    }
}





