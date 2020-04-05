package com.homework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.Check_list;
import com.homework.web.repository.Check_listRepository;
import com.homework.web.service.Check_listService;
@Service
public class Check_listServiceImpl implements Check_listService {

	@Autowired
	Check_listRepository 	check_listRepository;
	
	@Override
	public Check_list selectByAppointment_id(Integer appointment_id) {
		return check_listRepository.selectByAppointment_id(appointment_id);
	}

	@Override
	public Check_list insert(Check_list check_list) {
		return check_listRepository.save(check_list);
	}

	@Override
	public List<Check_list> selectByCheck_project_id(Integer check_project_id) {
		return check_listRepository.selectByCheck_project_id(check_project_id);
	}

	@Override
	public Check_list update(Check_list check_list) {
		return check_listRepository.save(check_list);
	}

	@Override
	public Check_list selectById(Integer id) {
		return check_listRepository.findById(id).get();
	}


}
