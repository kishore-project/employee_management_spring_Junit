package com.ideas2it.employeemanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeemanagementApplication {

	private static final Logger logger = LogManager.getLogger(EmployeemanagementApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(EmployeemanagementApplication.class, args);
		logger.info("Application Started");
	}

}
