package com.ideas2it.employeemanagement.employee.dto;

import com.ideas2it.employeemanagement.sport.dto.SportDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for Employee entity.
 * This class is used to transfer employee data between layers.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private int id;

    @NotBlank(message = "Name is Required")
    @Size(min=2, max=30, message = "Name should not exceed 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+([ ][a-zA-Z]+)*$", message = "Name should be alphabets" )
    private String name;

    @NotNull(message = "Date Of Birth Is Required")
    @Past(message = "Date of Birth id must be past")
    private LocalDate dob;

    private int age;

    @NotBlank(message = "Email ID is Required")
    @Email(regexp = "\\\\b[A-za-z0-9._%-]\"\n\"+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}\\\\b")
    private String emailId;

    @NotNull(message = "Department ID is Required")
    private int departmentID;

    private String departmentName;

    @NotBlank(message = "Street is Required")
    private String street;

    @NotBlank(message = "City is Required")
    private String city;

    @NotBlank(message = "State is Required")
    private String state;

    @NotBlank(message = "Zip is Required")
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}|[1-9]{1}[0-9]{3}\\\\s[0-9]{3}$")
    private String zip;

    private Set<SportDto> sports;
}
