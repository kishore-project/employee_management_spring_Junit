package com.ideas2it.employeemanagement.service;

import com.ideas2it.employeemanagement.exceptions.ResourceAlreadyExistsException;
import com.ideas2it.employeemanagement.exceptions.ResourceNotFoundException;
import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dao.SportRepository;
import com.ideas2it.employeemanagement.sport.dto.SportDto;
import com.ideas2it.employeemanagement.sport.service.SportServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;

import java.util.Optional;

import static org.apache.logging.log4j.core.impl.ThrowableFormatOptions.MESSAGE;
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


    @Test
    public void testLogMessage(CapturedOutput output) {
        sportService.logMessage(MESSAGE);
        Assertions.assertTrue(output.getOut().contains(MESSAGE));
    }

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
        //verify(sportService.getLogger()).info("Adding sport with name: {}", sportDto.getName());
    }

    @Test
    void addSport_SportAlreadyExists_ThrowsException() {
        when(sportRepository.existsByName(sportDto.getName())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> sportService.addSport(sportDto));

        //verify(sportService.getLogger()).error("Sport already exists with name: {}", sportDto.getName());
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

      //  verify(sportService.getLogger()).error("Sport not found with ID: {}", 1);
    }

    @Test
    void deleteSport_ValidId_DeletesSport() {
        when(sportRepository.findById(1)).thenReturn(Optional.of(sport));

        sportService.deleteSport(1);

        assertFalse(sport.isActive());
        verify(sportRepository).save(sport);
       // verify(sportService.getLogger()).info("Sport is deleted with name: {}", sport.getName());
    }

    @Test
    void deleteSport_InvalidId_ThrowsException() {
        when(sportRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sportService.deleteSport(1));

      //  verify(sportService.getLogger()).error("Sport not found with ID: {}", 1);
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

       // verify(sportService.getLogger()).error("Sport is already deleted: {}", inactiveSport.getName());
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

      //  verify(sportService.getLogger()).error("Sport not found with ID: {}", 2);
    }
}
