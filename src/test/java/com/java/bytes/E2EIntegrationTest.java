package com.java.bytes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.java.bytes.controller.PatientController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class E2EIntegrationTest {

	@Autowired
	private PatientController patientController;

	@Value(value = "${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void contextLoads() throws Exception {
		assertThat(patientController).isNotNull();
	}

	@Test
	public void createPatientTest() {
		String data = "{\"firstName\": \"Prabhu\",\"lastName\": \"Shan\",\"dateOfBirth\": \"2014-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);

		UUID response = restTemplate.postForObject(
				"http://localhost:" + port + "/patient/create", entity,
				UUID.class,
				Collections.emptyMap());
		assertThat(response != null);

		assertThat(entityManager.createNativeQuery("select count(1) from Patient where id = " + response)
				.getMaxResults() == 1);
	}

}
