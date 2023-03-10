package com.prabhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prabhu.patient.Patient;
import com.prabhu.patient.PatientServices;

@RestController("/patient")
public class PatientController {
	
	@Autowired
	private PatientServices patientService;
	
	
	@PostMapping("/add")
	public long addPatient(@RequestBody Patient patient) {
		
		return patientService.addPatient(patient);
		
	}
	
	

}
