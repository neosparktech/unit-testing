package com.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient.services.PatientVO;
import com.patient.services.PatientServices;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientServices patientService;
	
	
	@PostMapping("/add")
	public long addPatient(@RequestBody PatientVO patient) {
		
		return patientService.addPatient(patient);
		
	}
	
	@GetMapping("/get/{id}")
	public PatientVO getPatient(@PathVariable("id") long id) {
		
		return patientService.getPatient(id);
		
	}
	
	

}
