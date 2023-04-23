package com.java.bytes.patientServices;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
	
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}
