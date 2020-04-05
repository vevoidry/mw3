package com.homework.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment;
import com.homework.web.service.impl.AppointmentServiceImpl;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	AppointmentServiceImpl appointmentServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Appointment appointment) {
		if (appointmentServiceImpl.selectByPatient_idAppointment_time_id(appointment.getPatient_id(),
				appointment.getAppointment_time_id()) != null) {
			throw new RuntimeException("您已经预约过该医生了");
		}
		appointmentServiceImpl.insert(appointment);
		return new HashMap<String, String>();
	}
}
