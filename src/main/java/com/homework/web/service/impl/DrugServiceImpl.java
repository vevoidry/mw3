package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Drug;
import com.homework.web.repository.DrugRepository;
import com.homework.web.service.DrugService;

@Service
public class DrugServiceImpl implements DrugService {

	@Autowired
	DrugRepository drugRepository;

	@Override
	public List<Drug> selectAll() {
		return drugRepository.findAll();
	}

	@Override
	public Drug insert(Drug drug) {
		return drugRepository.save(drug);
	}

	@Override
	public Drug selectById(Integer id) {
		return drugRepository.findById(id).get();
	}

	@Override
	public Drug update(Drug drug) {
		return drugRepository.save(drug);
	}

}
