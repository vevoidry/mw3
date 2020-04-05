package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Diagnosis;
import com.homework.web.repository.DiagnosisRepository;
import com.homework.web.service.DiagnosisService;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

	@Autowired
	DiagnosisRepository diagnosisRepository;

	@Override
	public Diagnosis selectByAppointment_id(Integer appointment_id) {
		return diagnosisRepository.selectByAppointment_id(appointment_id);
	}

	@Override
	public Diagnosis insert(Diagnosis diagnosis) {
		return diagnosisRepository.save(diagnosis);
	}

	@Override
	public List<Object[]> disease_statistics() {
		return diagnosisRepository.disease_statistics();
	}

	@Override
	public List<Object[]> disease_statisticsByYear(String year) {
		return diagnosisRepository.disease_statisticsByYear(year);
	}

	@Override
	public List<Object[]> disease_statisticsByYearMonth(String year_month) {
		return diagnosisRepository.disease_statisticsByYearMonth(year_month);
	}

	@Override
	public Integer disease_statisticsByDisease_idGender(Integer disease_id, Boolean gender) {
		return diagnosisRepository.disease_statisticsByDisease_idGender(disease_id, gender);
	}

	@Override
	public Integer disease_statisticsByYearDisease_idGender(String year, Integer disease_id, Boolean gender) {
		return diagnosisRepository.disease_statisticsByYearDisease_idGender(year, disease_id, gender);
	}

	@Override
	public Integer disease_statisticsByYear_monthDisease_idGender(String year_month, Integer disease_id,
			Boolean gender) {
		return diagnosisRepository.disease_statisticsByYear_monthDisease_idGender(year_month, disease_id, gender);
	}

}
