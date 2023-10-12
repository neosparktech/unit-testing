package com.java.bytes;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.java.bytes.controller.PatientController;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
@WireMockTest(httpPort = 3434)
public class E2EIntegrationTest {

	private static final String APPLICATION_JSON = "application/json";

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
		assertTrue(patientController != null);
	}

	@Test
	public void createPatientTest() {
		String data = "{\"firstName\": \"John\",\"lastName\": \"Doe\",\"dateOfBirth\": \"1999-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);
		createStubforExternal_positiveResponse();
		UUID responseUUID = restTemplate.postForObject(
				"http://localhost:" + port + "/patient/create", entity,
				UUID.class,
				Collections.emptyMap());
		assertTrue(responseUUID != null);


		assertTrue(entityManager.createNativeQuery("select count(1) from Patient where id = ?")
				.setParameter(1, responseUUID)
				.getSingleResult().toString().equals("1"));
	}

	@Test
	public void getPatientTestWithSuccess() {
		String data = "{\"firstName\": \"Joe\",\"lastName\": \"Smith\",\"dateOfBirth\": \"1984-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);
		createStubforExternal_positiveResponse();
		UUID responseUUID = restTemplate.postForObject("http://localhost:" + port + "/patient/create", entity,
				UUID.class, Collections.emptyMap());
		assertTrue(responseUUID != null);

		ResponseEntity<String> result = restTemplate
				.getForEntity("http://localhost:" + port + "/patient/get/" + responseUUID, String.class);
		assertTrue(result.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void getPatientTestWithFailure() {

		ResponseEntity<String> result = restTemplate
				.getForEntity("http://localhost:" + port + "/patient/get/" + UUID.randomUUID(), String.class);
		assertTrue(result.getStatusCode().is5xxServerError());
	}

	@Test
	public void getPatientTestWithExternalTimeout() {

		String data = "{\"firstName\": \"John\",\"lastName\": \"Doe\",\"dateOfBirth\": \"1999-09-02\"}";
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(data, headers);
		createStubforExternal_timeoutResponse();
		ResponseEntity<String> result = restTemplate.postForEntity(
				"http://localhost:" + port + "/patient/create", entity, String.class, Collections.emptyMap());

		assertTrue(result.getBody().contains("User Info Failed"));
	}


	@Test
	public void givenJUnitManagedServer_whenMatchingURL_thenCorrect() throws IOException {

		stubFor(get(urlPathMatching("/baeldung/test")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", APPLICATION_JSON).withBody("\"testing-library\": \"WireMock\"")));

		ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:3434/baeldung/test", String.class);

		assertTrue(result.getStatusCode().is2xxSuccessful());
		log.info(result.getBody());
	}

	public void createStubforExternal_positiveResponse() {
		stubFor(get(urlPathMatching("/users/1")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", APPLICATION_JSON).withBody(
						"{\"id\": 2,\"name\": \"Ervin Howell\",\"username\": \"Antonette\",\"email\": \"Shanna@melissa.tv\"}")));

	}

	public void createStubforExternal_timeoutResponse() {
		stubFor(get(urlPathMatching("/users/1"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", APPLICATION_JSON).withBody(
						"{\"id\": 2,\"name\": \"Ervin Howell\",\"username\": \"Antonette\",\"email\": \"Shanna@melissa.tv\"}")
						.withFixedDelay(10_000)));

	}





}
