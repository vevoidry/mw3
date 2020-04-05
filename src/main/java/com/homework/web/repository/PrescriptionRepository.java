package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	@Query(value = "select * from prescription where appointment_id = :appointment_id", nativeQuery = true)
	List<Prescription> selectByAppointment_id(Integer appointment_id);

	@Query(value = "select * from prescription where appointment_id = :appointment_id and drug_id=:drug_id", nativeQuery = true)
	Prescription selectByAppointment_idDrug_id(Integer appointment_id, Integer drug_id);

	@Query(value = "select drug_id,sum(quantity) as the_sum from prescription group by drug_id order by the_sum desc limit 10", nativeQuery = true)
	List<Object[]> drug_statistics();
	
	@Query(value = "select drug_id,sum(quantity) as the_sum from prescription where DATE_FORMAT(time_day,'%Y')=:year group by drug_id order by the_sum desc limit 10", nativeQuery = true)
	List<Object[]> drug_statisticsByYear(String year);
	
	@Query(value = "select drug_id,sum(quantity) as the_sum from prescription where DATE_FORMAT(time_day,'%Y%m')=:year_month group by drug_id order by the_sum desc limit 10", nativeQuery = true)
	List<Object[]> drug_statisticsByYearMonth(String year_month);
}
