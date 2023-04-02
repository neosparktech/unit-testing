package com.prabhu;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prabhu.patient.AppointmentServices;
import com.prabhu.patient.Patient;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AppointmentServiceTest {

	@Spy
	private AppointmentServices appointmentService;


	@Test
	void test_appointment_date_not_in_past()
	{
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();
		appointmentService.bookAppointment(testPatient, LocalDateTime.of(2023, 4, 2, 10, 00));
	}

	@Test
	void test_appointment_date_in_past() {
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 3, 10, 00);
		try (MockedStatic<LocalDateTime> mocked = Mockito.mockStatic(LocalDateTime.class)) {
			mocked.when(LocalDateTime::now).thenReturn(fixedDateTime);
			appointmentService.bookAppointment(testPatient, fixedDateTime);
		}
	}

}
