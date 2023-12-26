package com.patient;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.CollectionUtils;

import com.patient.entities.Patient;
import com.patient.repo.PatientRepo;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
public class PatientServiceRepoTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private PatientRepo patientRepo;

	@Test
	void testInsertionUsingEntityManager() {
		Patient patient = Patient.builder().name("John Smith").email("john@example.com").build();
		testEntityManager
				.persist(patient);
		Optional<Patient> optionalPatient = patientRepo.findById(patient.getPatientId());


		if (optionalPatient.isPresent()) {
			assertTrue(optionalPatient.get().getName().equals("John Smith"));
		}
		else {
			fail("No Patient found");
		}
	}

	@Test
	@Sql("classpath:createPatient.sql")
	void testInsertionUsingSQL() {

		Optional<Patient> optionalPatient = patientRepo.findById(2L);
		if (optionalPatient.isPresent()) {
			assertTrue(optionalPatient.get().getName().equals("John Doe"));
			log.info("Patient ID {} ", optionalPatient.get().getPatientId());
		} else {
			fail("No Patient found");
		}
		// fail("Not yet implemented");
	}

	@Test
	@Sql("classpath:testJoinQueriesUsingSQL.sql")
	void testJoinQueriesUsingSQL() {
		Optional<Patient> optionalPatient = patientRepo.findById(100L);
		if (optionalPatient.isPresent()) {
			assertTrue(optionalPatient.get().getName().equals("John Doe"));
			assertTrue(!CollectionUtils.isEmpty(optionalPatient.get().getListAppointments()));
			log.info(optionalPatient.get().getListAppointments().get(0).toString());
		} else {
			fail("No Patient found");
		}
	}

	@Test
	@Sql("classpath:createPatient.sql")
	void testNamedQueries() {
		Patient patient = patientRepo.findPatientByEmailID("john@example.com");
		assertTrue(patient.getName().equals("John Doe"));

	}

}
