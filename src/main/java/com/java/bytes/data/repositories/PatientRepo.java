package com.java.bytes.data.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.bytes.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, UUID> {

	@Query("select p from Patient p where p.id = :id")
	public Patient getPatientInfoByID(UUID id);

}
