package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Prescription;

public interface PrescriptionService {
	List<Prescription> selectByAppointment_id(Integer appointment_id);
	
	Prescription insert(Prescription prescription);
	
	Prescription selectByAppointment_idDrug_id(Integer appointment_id, Integer drug_id);
	
	Prescription update(Prescription prescription);
	
	List<Prescription> selectAll();
	
	List<Object[]> drug_statistics();
	
	List<Object[]> drug_statisticsByYear(String year);
	
	List<Object[]> drug_statisticsByYearMonth(String year_month);
}
