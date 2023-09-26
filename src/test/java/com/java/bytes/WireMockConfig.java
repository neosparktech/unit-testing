package com.java.bytes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

//@Configuration
public class WireMockConfig {

	@Value("${wiremock.port}")
	private int wireMockPort;

	@Bean(initMethod = "start", destroyMethod = "stop")
	WireMockServer wireMockServer() {
		return new WireMockServer(wireMockConfig().port(wireMockPort));
	}

	@Bean
	WireMockConfiguration wireMockConfig() {
		return WireMockConfiguration.options().usingFilesUnderClasspath("wiremock").jettyStopTimeout(10000L)
				.asynchronousResponseEnabled(true);
	}
}
