package com.prabhu;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prabhu.patient.AppointmentServices;
import com.prabhu.patient.Patient;
import com.prabhu.patient.PatientServices;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

	@InjectMocks
	private PatientServices patientService;

	@Mock
	private AppointmentServices appointmentService;

//	@Autowired
//	private TestEntityManager testEntityManager;

	@Test
	@DisplayName("Test appointment booking with appointment Service returns true")
	void test_book_appointments_with_appointment_service_true() {
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();
		Mockito.when(appointmentService.bookAppointment(Mockito.any(Patient.class))).thenReturn(true);

		assertTrue("ok".equalsIgnoreCase(patientService.bookAppointments(testPatient)));
		
		ArgumentCaptor<Patient> normalizedPatientCaptor = ArgumentCaptor.forClass(Patient.class);
		Mockito.verify(appointmentService,times(1)).bookAppointment(normalizedPatientCaptor.capture());
		Patient capturedPatient = normalizedPatientCaptor.getValue();
		assertTrue("JOHN".equals(capturedPatient.getFirstName()));
		assertTrue("DOE".equals(capturedPatient.getLastName()));
		assertTrue(testPatient.getDateOfBirth().equals(capturedPatient.getDateOfBirth()));
		


	}

	@Test
	@DisplayName("Test appointment booking with appointment Service returns false")
	void test_book_appointments_with_appointment_service_false() {
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();
		Mockito.when(appointmentService.bookAppointment(Mockito.any(Patient.class))).thenReturn(false);
		assertTrue("failure".equalsIgnoreCase(patientService.bookAppointments(testPatient)));
	}
	
	@Test
	@DisplayName("Test appointment booking with appointment Service returns false")
	void test_book_appointment_with_anyPatient_appointment_service_false() {
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();
		Mockito.when(appointmentService.bookAppointment(Mockito.any(Patient.class))).thenReturn(false);
		assertTrue("failure".equalsIgnoreCase(patientService.bookAppointments(testPatient)));
	}
	
	
	@Test
	@DisplayName("Throw exception appointment booking with empty patient")
	void test_book_appointments_with_null_Patient() {
		Patient testPatient = Patient.builder().firstName(null).lastName(null).dateOfBirth(null).build();
		assertThrows(IllegalArgumentException.class, () -> patientService
				.bookAppointments(testPatient));

	}
	
	

}
