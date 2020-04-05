package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Check_list;
import com.homework.web.repository.AppointmentRepository;
import com.homework.web.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public Appointment insert(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment selectByPatient_idAppointment_time_id(Integer patient_id, Integer appointment_time_id) {
		return appointmentRepository.selectByPatient_idAppointment_time_id(patient_id, appointment_time_id);
	}

	@Override
	public List<Appointment> selectByPatient_id(Integer patient_id) {
		return appointmentRepository.selectByPatient_id(patient_id);
	}

	@Override
	public List<Appointment> selectByAppointment_time_id(Integer appointment_time_id) {
		return appointmentRepository.selectByAppointment_time_id(appointment_time_id);
	}

	@Override
	public Appointment selectById(Integer id) {
		return appointmentRepository.findById(id).get();
	}



}
