package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Check_list;

public interface Check_listService {

	Check_list selectByAppointment_id(Integer appointment_id);
	
	Check_list insert(Check_list check_list);
	
	List<Check_list> selectByCheck_project_id(Integer check_project_id);
	
	Check_list update(Check_list check_list);
	
	Check_list selectById(Integer id);
}
