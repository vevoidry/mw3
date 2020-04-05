package com.homework.web.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//病人进行挂号预约
@Data
@Entity
@Table(name = "appointment")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "patient_id")
	Integer patient_id;// 病人的id

	@Column(name = "appointment_time_id")
	Integer appointment_time_id;// 所挂号的时间的id

}
