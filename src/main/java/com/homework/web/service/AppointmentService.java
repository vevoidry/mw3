package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Check_list;

public interface AppointmentService {
	List<Appointment> selectByPatient_id(Integer patient_id);
//	
	Appointment insert(Appointment appointment);
//
	List<Appointment> selectByAppointment_time_id(Integer appointment_time_id);
	
	Appointment selectByPatient_idAppointment_time_id(Integer patient_id,Integer appointment_time_id);
	
	Appointment selectById(Integer id);
}
