package com.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.patient.services.AppointmentServices;
import com.patient.services.PatientVO;
import com.patient.services.User;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class AppointmentServiceTest {

	@Spy
	private AppointmentServices appointmentService;

	@Mock
	private RequestBodyEntity requestBodyEntity;

	@Mock
	private HttpRequestWithBody httpRequestWithBody;

	@Mock
	private HttpResponse<User> httpResponse;

	@Test
	void test_appointment_date_in_past() {
		PatientVO testPatient = PatientVO.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now())
				.build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 2, 10, 00);
		setMockUniRest();

		try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);
			mockedUnirest.when(() -> Unirest.post(anyString())).thenReturn(httpRequestWithBody);


			assertTrue(appointmentService.bookAppointment(testPatient, fixedDateTime));
		}
	}

	@Test
	void test_matched_name()
	{
		PatientVO testPatient = PatientVO.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now())
				.build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 2, 10, 00);
		setMockUniRest();

		try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);
			mockedUnirest.when(() -> Unirest.post(anyString())).thenReturn(httpRequestWithBody);

			assertTrue(appointmentService.bookAppointment(testPatient, fixedDateTime));
		}
	}

	@Test
	void test_unmatched_name() {
		PatientVO testPatient = PatientVO.builder().firstName("Thomas").lastName("Doe").dateOfBirth(LocalDate.now())
				.build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 2, 10, 00);
		setMockUniRest();

		try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class);
				MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);
			mockedUnirest.when(() -> Unirest.post(anyString())).thenReturn(httpRequestWithBody);

			assertFalse(appointmentService.bookAppointment(testPatient, fixedDateTime));
		}
	}

	@Test
	void test_appointment_date_not_in_past()
	{
		PatientVO testPatient = PatientVO.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now())
				.build();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.bookAppointment(testPatient, LocalDateTime.of(2023, 4, 2, 10, 00));

		});

		assertEquals("Appointment Datetime cannot be less than today date", exception.getMessage());

	}

	private void setMockUniRest() {

			when(httpRequestWithBody.header(anyString(), anyString())).thenReturn(httpRequestWithBody);
			when(httpRequestWithBody.body(any(PatientVO.class))).thenReturn(requestBodyEntity);
			when(requestBodyEntity.asObject((User.class))).thenReturn(httpResponse);
			when(httpResponse.getBody()).thenReturn(User.builder().name("John").build());


	}

}
