package com.java.bytes.data.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.bytes.entities.Patient;

public interface PatientRepo extends JpaRepository<Patient, UUID> {

}
