package com.homework.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Check_department;
import com.homework.web.pojo.Check_project;
import com.homework.web.service.impl.Check_departmentServiceImpl;

@Controller
@RequestMapping("/check_departments")
public class Check_departmentController {

	@Autowired
	Check_departmentServiceImpl check_departmentServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(String name) {
		Check_department check_department = new Check_department();
		check_department.setName(name.trim());
		if (check_department.getName().equals("")) {
			throw new RuntimeException("检查科室名不可为空");
		}
		Check_department check_department2 = check_departmentServiceImpl.selectByName(check_department.getName());
		if (check_department2 != null) {
			throw new RuntimeException("该检查科室已存在");
		}
		check_departmentServiceImpl.insert(check_department);
		return new HashMap<String, String>();
	}

}
