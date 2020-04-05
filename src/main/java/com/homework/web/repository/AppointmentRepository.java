package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Article;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	@Query(value = "select * from appointment where patient_id = :patient_id", nativeQuery = true)
	List<Appointment> selectByPatient_id(Integer patient_id);
//
	@Query(value = "select * from appointment where appointment_time_id = :appointment_time_id", nativeQuery = true)
	List<Appointment> selectByAppointment_time_id(Integer appointment_time_id);
	
	@Query(value = "select * from appointment where patient_id = :patient_id and appointment_time_id=:appointment_time_id", nativeQuery = true)
	Appointment selectByPatient_idAppointment_time_id(Integer patient_id,Integer appointment_time_id);
}
