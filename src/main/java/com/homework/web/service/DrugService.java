package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.Drug;

public interface DrugService {

	Drug insert(Drug drug);

	List<Drug> selectAll();

	Drug selectById(Integer id);

	Drug update(Drug drug);
}
