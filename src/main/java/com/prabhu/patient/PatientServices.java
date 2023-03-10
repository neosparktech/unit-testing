package com.prabhu.patient;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prabhu.repo.PatientRepo;

@Service
public class PatientServices {

	private static final String SUCCESSFULL = "ok";
	private static final String FAILURE = "failure";

	@Autowired
	private AppointmentServices appointmentServices;
	
	@Autowired
	private PatientRepo patientRepo;
	
	public Patient getPatient(long id) {
		Optional<com.prabhu.entities.Patient> patientEntity = patientRepo.findById(id);
		if(patientEntity.isPresent()) {
			com.prabhu.entities.Patient patient = patientEntity.get();
			return Patient.builder().firstName(patient.getName()).lastName(patient.getEmail()).build();
		}
		return null;
	}
	
	
	public long addPatient(Patient patient) {
		
		com.prabhu.entities.Patient patientEntity =  com.prabhu.entities.Patient.builder().name(patient.getFirstName()) .email(patient.getLastName()).build();
		patientRepo.save(patientEntity);
		
		return patientEntity.getId();
		
	}

	public String bookAppointments(Patient patient) {
		if (patient != null && StringUtils.hasText(patient.getFirstName())) {
			return postProcessing(patient);

		}

		throw new IllegalArgumentException("Patient firstName cannot be empty");

	}

	private String postProcessing(Patient patient) {
		Patient normalizedPatient = normalize(patient);
		boolean isAppointmentBooked = appointmentServices.bookAppointment(normalizedPatient);
		if (isAppointmentBooked) {
			return SUCCESSFULL;
		}
		return FAILURE;
	}

	private Patient normalize(Patient patient) {
		return Patient.builder().firstName(patient.getFirstName().toUpperCase())
				.lastName(StringUtils.capitalize(patient.getLastName().toUpperCase())).dateOfBirth(patient.getDateOfBirth()).build();

	}

}
