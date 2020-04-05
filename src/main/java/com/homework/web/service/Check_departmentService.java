package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Check_department;

public interface Check_departmentService {
	List<Check_department> selectAll();

	Check_department insert(Check_department check_department);
	
	Check_department selectByName(String name);
	
	Check_department selectById(Integer id);
	
	Check_department update(Check_department check_department);
	
	List<Check_department> selectByLeader_id(Integer leader_id);
}
