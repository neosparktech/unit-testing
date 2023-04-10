package com.java.bytes.patientServices;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentServices {

	public boolean bookAppointment(Patient patient, LocalDateTime appointmentDateTime) {

		log.info("LocalDateTime.now() {}", LocalDateTime.now());
		if (appointmentDateTime.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Appointment Datetime cannot be less than today date");
		}
		//Call some external service
		return true;
		
		
	}

}
