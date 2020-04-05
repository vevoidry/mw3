package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Article;
import com.homework.web.pojo.Diagnosis;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
	@Query(value = "select * from diagnosis where appointment_id = :appointment_id", nativeQuery = true)
	Diagnosis selectByAppointment_id(Integer appointment_id);

	@Query(value = "select disease_id,count(id) as the_count from diagnosis group by disease_id order by the_count desc limit 10", nativeQuery = true)
	List<Object[]> disease_statistics();

	@Query(value = "select count(id) from diagnosis  where disease_id=:disease_id and gender=:gender", nativeQuery = true)
	Integer disease_statisticsByDisease_idGender(Integer disease_id, Boolean gender);

	@Query(value = "select disease_id,count(id) as the_count from diagnosis  where DATE_FORMAT(time_day,'%Y')=:year  group by disease_id order by the_count desc limit 10", nativeQuery = true)
	List<Object[]> disease_statisticsByYear(String year);

	@Query(value = "select count(id) from diagnosis  where DATE_FORMAT(time_day,'%Y')=:year and disease_id=:disease_id and gender=:gender", nativeQuery = true)
	Integer disease_statisticsByYearDisease_idGender(String year,Integer disease_id, Boolean gender);

	@Query(value = "select disease_id,count(id) as the_count from diagnosis where DATE_FORMAT(time_day,'%Y%m')=:year_month  group by disease_id order by the_count desc limit 10", nativeQuery = true)
	List<Object[]> disease_statisticsByYearMonth(String year_month);
	
	@Query(value = "select count(id) from diagnosis  where DATE_FORMAT(time_day,'%Y%m')=:year_month  and disease_id=:disease_id and gender=:gender", nativeQuery = true)
	Integer disease_statisticsByYear_monthDisease_idGender(String year_month,Integer disease_id, Boolean gender);
}
