package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Check_project;

public interface Check_projectService {
	Check_project insert(Check_project check_project);
//	
	List<Check_project> selectAll();
//	
	List<Check_project> selectByCheck_department_id(Integer check_department_id);
	
	Check_project selectByCheck_department_idName(Integer check_department_id, String name);
	
	Check_project selectById(Integer id);
}
