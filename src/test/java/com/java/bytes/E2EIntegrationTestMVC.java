package com.java.bytes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class E2EIntegrationTestMVC {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EntityManager entityManager;



	@Test
	public void createPatientTest() throws IllegalStateException, UnsupportedEncodingException, Exception {
		String data = "{\"firstName\": \"Prabhu\",\"lastName\": \"Shan\",\"dateOfBirth\": \"2014-09-02\"}";


		String response = (
				mockMvc.perform(post("/patient/create").contentType(MediaType.APPLICATION_JSON).content(data))
						.andReturn().getResponse().getContentAsString());
				log.info("Log {}", response);

		assertThat(entityManager.createNativeQuery("select count(1) from Patient where id = " + response)
				.getMaxResults() == 1);
	}

}
