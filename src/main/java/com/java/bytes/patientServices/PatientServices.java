package com.java.bytes.patientServices;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.java.bytes.data.repositories.PatientRepo;
import com.java.bytes.entities.Patient;

@Service
public class PatientServices {

	private static final String SUCCESSFULL = "ok";
	private static final String FAILURE = "failure";

	@Autowired
	private AppointmentServices appointmentServices;

	@Autowired
	private PatientRepo patientRepo;

	public UUID createPatients(PatientDTO patientDTO) {

		Patient patient = Patient.builder().firstName(patientDTO.getFirstName()).lastName(patientDTO.getLastName())
				.dateOfBirth(patientDTO.getDateOfBirth()).build();
		return patientRepo.save(patient).getId();

	}

	public PatientDTO getPatient(UUID id) {
		Patient patient = patientRepo.getReferenceById(id);
		return PatientDTO.builder().firstName(patient.getFirstName()).lastName(patient.getLastName())
				.dateOfBirth(patient.getDateOfBirth()).build();

	}

	public String bookAppointments(PatientDTO patient, LocalDateTime appointmentDateTime) {
		if (patient != null && StringUtils.hasText(patient.getFirstName())) {
			return postProcessing(patient, appointmentDateTime);

		}

		throw new IllegalArgumentException("Patient firstName cannot be empty");

	}

	private String postProcessing(PatientDTO patient, LocalDateTime appointmentDateTime) {
		PatientDTO normalizedPatient = normalize(patient);
		boolean isAppointmentBooked = appointmentServices.bookAppointment(normalizedPatient, appointmentDateTime);
		if (isAppointmentBooked) {
			return SUCCESSFULL;
		}
		return FAILURE;
	}

	private PatientDTO normalize(PatientDTO patient) {
		return PatientDTO.builder().firstName(patient.getFirstName().toUpperCase())
				.lastName(StringUtils.capitalize(patient.getLastName().toUpperCase())).dateOfBirth(patient.getDateOfBirth()).build();

	}

}
