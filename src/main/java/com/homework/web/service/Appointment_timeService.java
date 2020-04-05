package com.homework.web.service;

import java.util.Date;
import java.util.List;

import com.homework.web.pojo.Appointment_time;

public interface Appointment_timeService {
	List<Appointment_time> selectByWorker_id(Integer worker_id);
//	
	Appointment_time insert(Appointment_time appointment_time);
//	
	List<Appointment_time> selectByTime_day(Date time_day);
	
	Appointment_time selectByWorker_idTime_dayTime_time(Integer worker_id,Date time_day,Boolean time_time);
	
	Appointment_time selectById(Integer id);
	
//	List<Appointment_time> selectByYear(Integer year);
}
