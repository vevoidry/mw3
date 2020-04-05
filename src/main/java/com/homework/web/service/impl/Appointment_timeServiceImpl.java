package com.homework.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Appointment_time;
import com.homework.web.repository.AppointmentRepository;
import com.homework.web.repository.Appointment_timeRepository;
import com.homework.web.service.Appointment_timeService;

@Service
public class Appointment_timeServiceImpl implements Appointment_timeService {

	@Autowired
	Appointment_timeRepository appointment_timeRepository;

	@Override
	public List<Appointment_time> selectByWorker_id(Integer worker_id) {
		return appointment_timeRepository.selectByWorker_id(worker_id);
	}

	@Override
	public Appointment_time insert(Appointment_time appointment_time) {
		return appointment_timeRepository.save(appointment_time);
	}

	@Override
	public List<Appointment_time> selectByTime_day(Date time_day) {
		return appointment_timeRepository.selectByTime_day(time_day);
	}

	@Override
	public Appointment_time selectByWorker_idTime_dayTime_time(Integer worker_id, Date time_day, Boolean time_time) {
		return appointment_timeRepository.selectByWorker_idTime_dayTime_time(worker_id, time_day, time_time);
	}

	@Override
	public Appointment_time selectById(Integer id) {
		return appointment_timeRepository.findById(id).get();
	}

//	@Override
//	public List<Appointment_time> selectByYear(Integer year) {
//		return appointment_timeRepository.selectByYear(year);
//	}

}
