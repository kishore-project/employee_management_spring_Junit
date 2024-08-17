package com.ideas2it.employeemanagement.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for department entity.
 * This class is used to transfer department data between layers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {
    private int id;
    @NotBlank(message = "Name is Required")
    @Size(min=2, max=10, message = "Name should not exceed 10 characters")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should be alphabets" )
    private String name;

}

