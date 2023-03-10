package com.prabhu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabhu.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {

}
