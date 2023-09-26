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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.java.bytes.controller.PatientController;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
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
		String data = "{\"firstName\": \"John\",\"lastName\": \"Doe\",\"dateOfBirth\": \"1999-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);

		UUID responseUUID = restTemplate.postForObject(
				"http://localhost:" + port + "/patient/create", entity,
				UUID.class,
				Collections.emptyMap());
		assertThat(responseUUID != null);

		assertThat(entityManager.createNativeQuery("select count(1) from Patient where id = " + responseUUID)
				.getMaxResults() == 1);
	}

	@Test
	public void getPatientTestWithSuccess() {
		String data = "{\"firstName\": \"Joe\",\"lastName\": \"Smith\",\"dateOfBirth\": \"1984-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);

		UUID responseUUID = restTemplate.postForObject("http://localhost:" + port + "/patient/create", entity,
				UUID.class, Collections.emptyMap());
		assertThat(responseUUID != null);

		ResponseEntity<String> result = restTemplate
				.getForEntity("http://localhost:" + port + "/patient/get/" + responseUUID, String.class);
		assertThat(result.getStatusCode().equals(200));
	}

	@Test
	public void getPatientTestWithFailure() {

		ResponseEntity<String> result = restTemplate
				.getForEntity("http://localhost:" + port + "/patient/get/" + UUID.randomUUID(), String.class);
		assertThat(result.getStatusCode().equals(500));
	}

}
