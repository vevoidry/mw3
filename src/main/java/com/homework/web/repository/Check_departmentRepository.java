package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Check_department;

public interface Check_departmentRepository extends JpaRepository<Check_department, Integer> {
	@Query(value = "select * from check_department where name = :name", nativeQuery = true)
	Check_department selectByName(String name);
	
	@Query(value = "select * from check_department where leader_id = :leader_id", nativeQuery = true)
	List<Check_department> selectByLeader_id(Integer leader_id);
}
