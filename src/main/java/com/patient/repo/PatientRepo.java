package com.patient.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.patient.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {

	@Query("select p from Patient p where lower(p.email) = lower(:emailId)")
	public Patient findPatientByEmailID(@Param("emailId") String emailId);

}
