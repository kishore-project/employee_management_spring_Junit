package com.ideas2it.employeemanagement.utilities;

import java.time.LocalDate;
import java.time.Period;

/*
 *<p>
 * Validating various type of input.
 * </p>
 * @author  Kishore
 */
public class Validator {

    /**
     * Calculates the age based on the provided date of birth (DOB).
     *
     * @param dob The date of birth as a LocalDate.
     * @return The age in years.
     */
    public static int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
