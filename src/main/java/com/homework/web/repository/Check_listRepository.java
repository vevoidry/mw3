package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Check_list;

public interface Check_listRepository extends JpaRepository<Check_list, Integer> {
	@Query(value = "select * from check_list where appointment_id = :appointment_id", nativeQuery = true)
	Check_list selectByAppointment_id(Integer appointment_id);

	@Query(value = "select * from check_list where check_project_id = :check_project_id", nativeQuery = true)
	List<Check_list> selectByCheck_project_id(Integer check_project_id);
}
