package com.patient.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {

}
