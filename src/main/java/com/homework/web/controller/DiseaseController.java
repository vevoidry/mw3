package com.homework.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Disease;
import com.homework.web.service.impl.DiseaseServiceImpl;

@Controller
@RequestMapping("/diseases")
public class DiseaseController {

	@Autowired
	DiseaseServiceImpl diseaseServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Disease disease) {
		// 判断名字是否为空
		disease.setName(disease.getName().trim());
		if (disease.getName().equals("")) {
			throw new RuntimeException("名字不可为空");
		}
		// 判断该名字是否已存在
		Disease disease2 = diseaseServiceImpl.selectByParent_idRankName(disease.getParent_id(), disease.getRank(),
				disease.getName());
		if (disease2 != null) {
			throw new RuntimeException("该名字已被使用");
		}
		diseaseServiceImpl.insert(disease);
//		String name = disease.getName();
//		String[] names = name.split(" ");
//		for (int i = 0; i < names.length; i++) {
//			System.out.println(names[i]);
//			Disease disease2 = new Disease();
//			disease2.setName(names[i]);
//			disease2.setParent_id(disease.getParent_id());
//			disease2.setRank(disease.getRank());
//			diseaseServiceImpl.insert(disease2);
//		}
		return new HashMap<String, String>();
	}

}
