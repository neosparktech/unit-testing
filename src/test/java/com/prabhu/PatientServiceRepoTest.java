package com.prabhu;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.prabhu.entities.Patient;
import com.prabhu.repo.PatientRepo;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
public class PatientServiceRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PatientRepo patientRepo;

	@Test
	void test() {
		Patient patient = Patient.builder().name("prabhu").email("prabhu@test.com").build();
		entityManager
				.persist(patient);
		Optional<Patient> optionalPatient = patientRepo.findById(patient.getId());
		if (optionalPatient.isPresent()) {
			assertTrue(optionalPatient.get().getName().equals("prabhu"));
			log.info("Patient ID {} ", optionalPatient.get().getId());
		}
		else {
			fail("No Patient found");
		}
		//fail("Not yet implemented");
	}

}
