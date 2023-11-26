package com.patient.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.patient.entities.Patient;
import com.patient.repo.PatientRepo;



@Service
public class PatientServices {

	private static final String SUCCESSFULL = "ok";
	private static final String FAILURE = "failure";

	@Autowired
	private AppointmentServices appointmentServices;
	
	@Autowired
	private PatientRepo patientRepo;
	
	public PatientVO getPatient(long id) {
		Optional<Patient> patientEntity = patientRepo.findById(id);
		if(patientEntity.isPresent()) {
			Patient patient = patientEntity.get();
			return PatientVO.builder().firstName(patient.getName()).lastName(patient.getEmail()).build();
		}
		return null;
	}
	
	
	public long addPatient(PatientVO patient) {
		
		Patient patientEntity = Patient.builder().name(patient.getFirstName()).email(patient.getLastName()).build();
		patientRepo.save(patientEntity);
		
		return patientEntity.getPatientId();
		
	}

	public String bookAppointments(PatientVO patient) {
		if (patient != null && StringUtils.hasText(patient.getFirstName())) {
			return postProcessing(patient);

		}

		throw new IllegalArgumentException("Patient firstName cannot be empty");

	}

	private String postProcessing(PatientVO patient) {
		PatientVO normalizedPatient = normalize(patient);
		boolean isAppointmentBooked = appointmentServices.bookAppointment(normalizedPatient,
				LocalDateTime.now());
		if (isAppointmentBooked) {
			return SUCCESSFULL;
		}
		return FAILURE;
	}

	private PatientVO normalize(PatientVO patient) {
		return PatientVO.builder().firstName(patient.getFirstName().toUpperCase())
				.lastName(StringUtils.capitalize(patient.getLastName().toUpperCase())).dateOfBirth(patient.getDateOfBirth()).build();

	}

}
