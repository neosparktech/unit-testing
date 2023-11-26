package com.patient.services;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientVO {
	
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}
