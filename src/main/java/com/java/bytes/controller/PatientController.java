package com.java.bytes.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.bytes.patientServices.PatientDTO;
import com.java.bytes.patientServices.PatientServices;

@RestController
@RequestMapping(path = "patient")
public class PatientController {

	@Autowired
	private PatientServices patientService;

	@PostMapping("create")
	public UUID createPatients(@RequestBody PatientDTO patientDTO) {
		return patientService.createPatients(patientDTO);

	}

	@GetMapping("get/${id}")
	public PatientDTO createPatients(@PathVariable UUID id) {
		return patientService.getPatient(id);

	}

}
