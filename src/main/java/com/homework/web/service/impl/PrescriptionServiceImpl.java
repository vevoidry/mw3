package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Prescription;
import com.homework.web.repository.PrescriptionRepository;
import com.homework.web.service.PrescriptionService;
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	PrescriptionRepository 	prescriptionRepository;
	
	@Override
	public List<Prescription> selectByAppointment_id(Integer appointment_id) {
		return prescriptionRepository.selectByAppointment_id(appointment_id);
	}

	@Override
	public Prescription insert(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}

	@Override
	public Prescription selectByAppointment_idDrug_id(Integer appointment_id, Integer drug_id) {
		return prescriptionRepository.selectByAppointment_idDrug_id(appointment_id, drug_id);
	}

	@Override
	public Prescription update(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}

	@Override
	public List<Prescription> selectAll() {
		return prescriptionRepository.findAll();
	}

	@Override
	public List<Object[]> drug_statistics() {
		return prescriptionRepository.drug_statistics();
	}

	@Override
	public List<Object[]> drug_statisticsByYear(String year) {
		return prescriptionRepository.drug_statisticsByYear(year);
	}

	@Override
	public List<Object[]> drug_statisticsByYearMonth(String year_month) {
		return prescriptionRepository.drug_statisticsByYearMonth(year_month);
	}

	
}
