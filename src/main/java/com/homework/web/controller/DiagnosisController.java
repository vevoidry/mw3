package com.homework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Appointment_time;
import com.homework.web.pojo.Diagnosis;
import com.homework.web.pojo.User;
import com.homework.web.service.impl.AppointmentServiceImpl;
import com.homework.web.service.impl.Appointment_timeServiceImpl;
import com.homework.web.service.impl.DiagnosisServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/diagnosiss")
public class DiagnosisController {

	@Autowired
	DiagnosisServiceImpl diagnosisServiceImpl;
	@Autowired
	Appointment_timeServiceImpl 	appointment_timeServiceImpl;
	@Autowired
	AppointmentServiceImpl 	appointmentServiceImpl;
	@Autowired
	UserServiceImpl 	userServiceImpl;
	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Integer disease_id, String remark, Integer appointment_id) throws Exception {
		if (disease_id == null) {
			throw new RuntimeException("请先选择疾病类型项目");
		}
		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setDisease_id(disease_id);
		diagnosis.setRemark(remark);
		diagnosis.setAppointment_id(appointment_id);
		Appointment appointment = appointmentServiceImpl.selectById(appointment_id);
		Appointment_time appointment_time = appointment_timeServiceImpl.selectById(appointment.getAppointment_time_id());
		diagnosis.setTime_day(appointment_time.getTime_day());
		User user = userServiceImpl.selectById(appointment.getPatient_id());
		diagnosis.setGender(user.getGender());
		diagnosisServiceImpl.insert(diagnosis);
		return new HashMap<String, String>();
	}
}
