package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Check_project;
import com.homework.web.repository.Check_projectRepository;
import com.homework.web.service.Check_projectService;

@Service
public class Check_projectServiceImpl implements Check_projectService {

	@Autowired
	Check_projectRepository check_projectRepository;

	@Override
	public Check_project insert(Check_project check_project) {
		return check_projectRepository.save(check_project);
	}

	@Override
	public List<Check_project> selectByCheck_department_id(Integer check_department_id) {
		return check_projectRepository.selectByCheck_department_id(check_department_id);
	}

	@Override
	public List<Check_project> selectAll() {
		return check_projectRepository.findAll();
	}

	@Override
	public Check_project selectByCheck_department_idName(Integer check_department_id, String name) {
		return check_projectRepository.selectByCheck_department_idName(check_department_id, name);
	}

	@Override
	public Check_project selectById(Integer id) {
		return check_projectRepository.findById(id).get();
	}

}
