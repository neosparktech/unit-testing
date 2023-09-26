package com.java.bytes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.java.bytes.controller.PatientController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(PatientController.class)
public class E2EIntegrationTestMVC {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;


	@Test
	public void createPatientTest() throws IllegalStateException, UnsupportedEncodingException, Exception {
		String data = "{\"firstName\": \"Prabhu\",\"lastName\": \"Shan\",\"dateOfBirth\": \"2014-09-02\"}";


		String response = (
				mockMvc.perform(post("/patient/create").contentType(MediaType.APPLICATION_JSON).content(data))
						.andReturn().getResponse().getContentAsString());
				log.info("Log {}", response);


	}

}
