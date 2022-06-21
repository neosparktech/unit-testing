package com.prabhu.patient;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}
