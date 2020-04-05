package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Check_project;

public interface Check_projectRepository extends JpaRepository<Check_project, Integer> {
	@Query(value = "select * from check_project where check_department_id = :check_department_id", nativeQuery = true)
	List<Check_project> selectByCheck_department_id(Integer check_department_id);

	@Query(value = "select * from check_project where check_department_id = :check_department_id and name=:name", nativeQuery = true)
	Check_project selectByCheck_department_idName(Integer check_department_id, String name);
}
