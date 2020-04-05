package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Disease;
import com.homework.web.repository.DiseaseRepository;
import com.homework.web.service.DiseaseService;

@Service
public class DiseaseServiceImpl implements DiseaseService {

	@Autowired
	DiseaseRepository diseaseRepository;

	@Override
	public List<Disease> selectByRank(Integer rank) {
		return diseaseRepository.selectByRank(rank);
	}

	@Override
	public List<Disease> selectByParent_id(Integer parent_id) {
		return diseaseRepository.selectByParent_id(parent_id);
	}

	@Override
	public Disease insert(Disease disease) {
		return diseaseRepository.save(disease);
	}

	@Override
	public Disease selectByParent_idRankName(Integer parent_id, Integer rank, String name) {
		return diseaseRepository.selectByParent_idRankName(parent_id, rank, name);
	}

	@Override
	public Disease selectById(Integer id) {
		return diseaseRepository.findById(id).get();
	}

}
