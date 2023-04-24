package com.java.bytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.bytes.patientServices.AppointmentServices;
import com.java.bytes.patientServices.PatientDTO;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

	@Spy
	private AppointmentServices appointmentService;


	@Test
	public void test_appointment_date_in_past() {
		PatientDTO testPatient = PatientDTO.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 2, 10, 00);
		try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);

			appointmentService.bookAppointment(testPatient, fixedDateTime);
		}
	}

	@Test
	public void test_appointment_date_not_in_past()
	{
		PatientDTO testPatient = PatientDTO.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.bookAppointment(testPatient, LocalDateTime.of(2023, 4, 2, 10, 00));

		});

		assertEquals("Appointment Datetime cannot be less than today date", exception.getMessage());

	}

}
