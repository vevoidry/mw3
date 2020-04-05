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

//医生设置的预约时间
@Data
@Entity
@Table(name = "appointment_time")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Appointment_time {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "worker_id")
	Integer worker_id;// 医生的id

	@Column(name = "time_day")
	Date time_day;// 日期

	@Column(name = "time_time")
	Boolean time_time;// 上午或下午，true为上午，false为下午

}
