package com.homework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment_time;
import com.homework.web.service.impl.Appointment_timeServiceImpl;

@Controller
@RequestMapping("/appointment_times")
public class Appointment_timeController {

	@Autowired
	Appointment_timeServiceImpl appointment_timeServiceImpl;

	// 新增挂号时间
	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(String time_day, Boolean time_time, Integer worker_id) throws Exception {
		Appointment_time appointment_time = new Appointment_time();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(time_day);
		appointment_time.setTime_day(date);
		appointment_time.setTime_time(time_time);
		appointment_time.setWorker_id(worker_id);
		if (appointment_timeServiceImpl.selectByWorker_idTime_dayTime_time(appointment_time.getWorker_id(),
				appointment_time.getTime_day(), appointment_time.getTime_time()) != null) {
			throw new RuntimeException("您已经设置过该挂号时间了");
		}
		appointment_timeServiceImpl.insert(appointment_time);
		return new HashMap<String, String>();
	}

}
