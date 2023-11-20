package com.patient.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long appointmentId;
	@ManyToOne(optional = false)
	@JoinColumn(name = "patientId")
	@Exclude
	private Patient patient;
	private LocalDateTime appointmentDateTime;
    
  
}