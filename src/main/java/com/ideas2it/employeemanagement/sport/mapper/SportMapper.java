package com.ideas2it.employeemanagement.sport.mapper;


import com.ideas2it.employeemanagement.model.Sport;
import com.ideas2it.employeemanagement.sport.dto.SportDto;

/**
 * A utility class for mapping between sport and sportDto objects,
 */
public class SportMapper {

    /**
     * Converts a sport entity to an SportDto.
     *
     * @param sport The sport entity to be converted.
     * @return The corresponding SportDto.
     */
    public static SportDto mapToSportDto(Sport sport) {
        return SportDto.builder()
                .id(sport.getId())
                .name(sport.getName())
                .build();
    }
    /**
     * Converts an SportDto to a sport entity.
     *
     * @param sportDto The SportDto to be converted.
     * @return The corresponding sport entity.
     */
    public static Sport mapToSport(SportDto sportDto) {
        return Sport.builder()
                .id(sportDto.getId())
                .name(sportDto.getName())
                .isActive(true)
                .build();
    }
}
