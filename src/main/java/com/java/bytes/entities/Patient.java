package com.java.bytes.entities;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Patient")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patient {

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

}

