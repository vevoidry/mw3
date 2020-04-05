package com.homework.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Check_project;
import com.homework.web.service.impl.Check_projectServiceImpl;

@Controller
@RequestMapping("/check_projects")
public class Check_projectController {

	@Autowired
	Check_projectServiceImpl check_projectServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Check_project check_project) {
		check_project.setName(check_project.getName().trim());
		if (check_project.getCheck_department_id() == null) {
			throw new RuntimeException("检查项目所属检查科室不可为空");
		}
		if (check_project.getName().equals("")) {
			throw new RuntimeException("检查项目名不可为空");
		}
		Check_project check_project2 = check_projectServiceImpl
				.selectByCheck_department_idName(check_project.getCheck_department_id(), check_project.getName());
		if (check_project2 != null) {
			throw new RuntimeException("该检查项目已存在");
		}
		check_projectServiceImpl.insert(check_project);
//		String[] names = check_project.getName().split(" ");
//		for (int i = 0; i < names.length; i++) {
//			System.out.println(names[i]);
//			Check_project check_project2 = new Check_project();
//			check_project2.setName(names[i]);
//			check_project2.setCheck_department_id(check_project.getCheck_department_id());
//			check_projectServiceImpl.insert(check_project2);
//		}
		return new HashMap<String, String>();
	}

}
