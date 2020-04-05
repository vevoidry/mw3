package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Diagnosis;

public interface DiagnosisService {
	Diagnosis selectByAppointment_id(Integer appointment_id);

	Diagnosis insert(Diagnosis diagnosis);

	List<Object[]> disease_statistics();

	List<Object[]> disease_statisticsByYear(String year);

	List<Object[]> disease_statisticsByYearMonth(String year_month);
	
	Integer disease_statisticsByDisease_idGender(Integer disease_id, Boolean gender);
	
	Integer disease_statisticsByYearDisease_idGender(String year,Integer disease_id, Boolean gender);
	
	Integer disease_statisticsByYear_monthDisease_idGender(String year_month,Integer disease_id, Boolean gender);
}
