package com.patient.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long patientId;
    private String name;
    private String email;
	@OneToMany(mappedBy = "patient")
	private List<Appointment> listAppointments;
    
  
}