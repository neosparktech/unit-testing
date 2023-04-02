package com.prabhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhu.patient.Patient;
import com.prabhu.patient.PatientServices;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientServices patientService;
	
	
	@PostMapping("/add")
	public long addPatient(@RequestBody Patient patient) {
		
		return patientService.addPatient(patient);
		
	}
	
	@GetMapping("/get/{id}")
	public Patient getPatient(@PathVariable("id") long id) {
		
		return patientService.getPatient(id);
		
	}
	
	

}
