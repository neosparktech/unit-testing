package com.prabhu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prabhu.patient.Patient;
import com.prabhu.patient.PatientServices;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@RestController("/patient")
public class UnitTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitTestingApplication.class, args);
	}
	
	@Autowired
	private PatientServices patientService;
	
	
	@PostMapping("/add")
	public long addPatient(@RequestBody Patient patient) {
		
		return patientService.addPatient(patient);
		
	}

}
