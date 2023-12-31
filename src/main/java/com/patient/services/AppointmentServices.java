package com.patient.services;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentServices {

	public boolean bookAppointment(PatientVO patient, LocalDateTime appointmentDateTime) {

		log.info("LocalDateTime.now() {}", LocalDateTime.now());
		if (appointmentDateTime.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Appointment Datetime cannot be less than today date");
		}
		//Call some external service
		HttpResponse<User> httpResponseAsUser = Unirest.post("https://dummyjson.com/posts/1")
				.header("Content-Type", "application/json")
				.header("X-RAW-HEADER", "Some headers").body(patient)
				.asObject(User.class);

		return Optional.ofNullable(httpResponseAsUser.getBody()).filter(s -> patient.getFirstName().equals(s.getName()))
				.isPresent();

	}

}
