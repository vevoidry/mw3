package com.homework.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Check_list;
import com.homework.web.pojo.Check_project;
import com.homework.web.pojo.User;
import com.homework.web.service.impl.AppointmentServiceImpl;
import com.homework.web.service.impl.Check_listServiceImpl;
import com.homework.web.service.impl.Check_projectServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/check_lists")
public class Check_listController {

	@Autowired
	Check_listServiceImpl check_listServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	Check_projectServiceImpl check_projectServiceImpl;
	@Autowired
	AppointmentServiceImpl appointmentServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Integer check_project_id, String time_day_string, Integer appointment_id)
			throws Exception {
		if (check_project_id == null) {
			throw new RuntimeException("请先选择检查项目");
		}
		if (time_day_string.equals("")) {
			throw new RuntimeException("请确定检查日期");
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date time_day = simpleDateFormat.parse(time_day_string);
		Check_list check_list = new Check_list();
		check_list.setCheck_project_id(check_project_id);
		check_list.setTime_day(time_day);
		check_list.setAppointment_id(appointment_id);
		check_listServiceImpl.insert(check_list);
		return new HashMap<String, String>();
	}

	@GetMapping("/{id:[0-9]*}")
	public String get(@PathVariable Integer id, Model model) {
		Check_list check_list = check_listServiceImpl.selectById(id);
		Appointment appointment = appointmentServiceImpl.selectById(check_list.getAppointment_id());
		User user = userServiceImpl.selectById(appointment.getPatient_id());
		Check_project check_project = check_projectServiceImpl.selectById(check_list.getCheck_project_id());
		model.addAttribute("check_list", check_list);
		model.addAttribute("user", user);
		model.addAttribute("check_project", check_project);
		return "check_list";
	}
}
