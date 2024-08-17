package com.ideas2it.employeemanagement.sport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for sport entity.
 * This class is used to transfer sport data between layers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SportDto {

    private int id;
    @NotBlank(message = "Name is Required")
    @Size(min=2, max=20, message = "Name should not exceed 20 characters")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should be alphabets" )
    private String name;
}
