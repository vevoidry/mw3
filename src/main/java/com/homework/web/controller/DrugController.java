package com.homework.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Drug;
import com.homework.web.service.impl.DrugServiceImpl;

@Controller
@RequestMapping("/drugs")
public class DrugController {

	@Autowired
	DrugServiceImpl drugServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Drug drug, String start_time_string) throws Exception {
		if (start_time_string.equals("")) {
			throw new RuntimeException("生产日期不可为空");
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(start_time_string);
		drug.setStart_time(date);
		drug.setName(drug.getName().trim());
		drug.setSpecification(drug.getSpecification().trim());
		drug.setSupplier_number(drug.getSupplier_number().trim());
		drug.setUnit(drug.getUnit().trim());
		if (drug.getName().equals("")) {
			throw new RuntimeException("药品名不可为空");
		}
		if (drug.getSupplier_number().equals("")) {
			throw new RuntimeException("供应商编号不可为空");
		}
		if (drug.getSpecification().equals("")) {
			throw new RuntimeException("规格不可为空");
		}
		if (drug.getUnit().equals("")) {
			throw new RuntimeException("单位不可为空");
		}
		if (drug.getPrice() == null) {
			throw new RuntimeException("单价不可为空");
		}
		if (drug.getQuantity() == null) {
			throw new RuntimeException("库存不可为空");
		}
		if (drug.getValidity_time() == null) {
			throw new RuntimeException("有效期不可为空");
		}
		drugServiceImpl.insert(drug);
		return new HashMap<String, String>();
	}
}
