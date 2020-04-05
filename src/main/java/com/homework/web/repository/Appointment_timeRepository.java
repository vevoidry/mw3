package com.homework.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Appointment_time;
import com.homework.web.pojo.Article;

public interface Appointment_timeRepository extends JpaRepository<Appointment_time, Integer> {
	@Query(value = "select * from appointment_time where worker_id = :worker_id", nativeQuery = true)
	List<Appointment_time> selectByWorker_id(Integer worker_id);
//
	@Query(value = "select * from appointment_time where time_day = :time_day", nativeQuery = true)
	List<Appointment_time> selectByTime_day(Date time_day);
	
	@Query(value = "select * from appointment_time where worker_id = :worker_id and time_day=:time_day and time_time=:time_time", nativeQuery = true)
	Appointment_time selectByWorker_idTime_dayTime_time(Integer worker_id,Date time_day,Boolean time_time);
	
//	@Query(value = "select * from appointment_time where DATE_FORMAT(time_day,'%Y')=:year", nativeQuery = true)
//	List<Appointment_time> selectByYear(Integer year);
}
