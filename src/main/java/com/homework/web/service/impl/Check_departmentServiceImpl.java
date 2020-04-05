package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Check_department;
import com.homework.web.repository.Check_departmentRepository;
import com.homework.web.service.Check_departmentService;

@Service
public class Check_departmentServiceImpl implements Check_departmentService {
	@Autowired
	Check_departmentRepository check_departmentRepository;

	@Override
	public List<Check_department> selectAll() {
		return check_departmentRepository.findAll();
	}

	@Override
	public Check_department insert(Check_department check_department) {
		return check_departmentRepository.save(check_department);
	}

	@Override
	public Check_department selectByName(String name) {
		return check_departmentRepository.selectByName(name);
	}

	@Override
	public Check_department selectById(Integer id) {
		return check_departmentRepository.findById(id).get();
	}

	@Override
	public Check_department update(Check_department check_department) {
		return check_departmentRepository.save(check_department);
	}

	@Override
	public List<Check_department> selectByLeader_id(Integer leader_id) {
		return check_departmentRepository.selectByLeader_id(leader_id);
	}

}
