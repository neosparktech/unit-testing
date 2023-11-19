package com.prabhu;


import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.prabhu.repo.PatientRepo;

@DataJpaTest
public class PatientServiceRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PatientRepo patientRepo;

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
