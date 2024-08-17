package com.ideas2it.employeemanagement.sport.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.employee.dto.EmployeeDto;
import com.ideas2it.employeemanagement.employee.mapper.EmployeeMapper;
import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dao.SportRepository;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.mapper.SportMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Implement if SportService interface to handle sport-related operations.
 * </p>
 * @author  Kishore
 */
@Service
public class SportServiceImpl implements SportService {
    @Autowired
    private SportRepository sportRepository;

    private static final Logger logger = LogManager.getLogger(SportServiceImpl.class);
    public void logMessage(String message) {
        logger.info(message);
    }

    @Override
    public SportDto addSport(SportDto sportDto) {
        if (sportRepository.existsByName(sportDto.getName())) {
            logger.error("Sport already exists with name: {}", sportDto.getName());
            throw new ResourceAlreadyExistsException("Sport already exists with name: " + sportDto.getName());
        }
        Sport sport = SportMapper.mapToSport(sportDto);
        Sport createdSport = sportRepository.save(sport);
        logger.info("Adding sport with name: {}", sportDto.getName());
        return SportMapper.mapToSportDto(createdSport);
    }

    @Override
    public List<SportDto> getAllSports() {
        Iterable<Sport> sports = sportRepository.findAll();
        List<SportDto> sportDtos = new ArrayList<>();
        for (Sport sport : sports) {
            if (sport.isActive()) {
                sportDtos.add(SportMapper.mapToSportDto(sport));
            }
        }
        logger.info("Retrieving list of all active sports");
        return sportDtos;
    }

    @Override
    public SportDto getSportById(int id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found with ID: " + id));
        if (!sport.isActive()) {
            logger.error("Sport is deleted with ID: {}", id);
            throw new ResourceNotFoundException("Sport is deleted with ID: " + id);
        }
        logger.info("Retrieving sport with ID: {}", id);
        return SportMapper.mapToSportDto(sport);
    }

    @Override
    public SportDto updateSport(int id, SportDto sportDto) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found with ID: " + id));
        if (!existingSport.isActive()) {
            logger.error("Cannot update a deleted sport with ID: {}", id);
            throw new ResourceNotFoundException("Cannot update a deleted sport with ID: " + id);
        }
        existingSport.setName(sportDto.getName());
        Sport updatedSport = sportRepository.save(existingSport);
        logger.info("Updated sport with name: {}", sportDto.getName());
        return SportMapper.mapToSportDto(updatedSport);
    }

    @Override
    public void deleteSport(int id) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found with ID: " + id));

        if (!existingSport.isActive()) {
            throw new IllegalStateException("Sport is already deleted: " + existingSport.getName());
        }

        existingSport.setActive(false);
        sportRepository.save(existingSport);
        logger.info("Sport is deleted with name: {}", existingSport.getName());
    }

    @Override
    public List<EmployeeDto> getEmployeesBySportId(int sportId) {
        Sport sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found with ID: " + sportId));

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : sport.getEmployees()) {
            if (employee.isActive()) {
                employeeDtos.add(EmployeeMapper.mapToEmployeeDto(employee));
            }
        }
        logger.info("Retrieving list of all active employees in sport with name: {}", sport.getName());
        return employeeDtos;
    }

}
