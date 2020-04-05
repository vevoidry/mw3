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

//检查单
@Data
@Entity
@Table(name = "check_list")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Check_list {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "appointment_id")
	Integer appointment_id;// 所属挂号单的id

	@Column(name = "check_project_id")
	Integer check_project_id;// 所属检查项目的id

	@Column(name = "time_day")
	Date time_day;// 进行检查的时间

	@Column(name = "time_real")
	Date time_real;// 出检查结果的时间

	@Column(name = "result")
	String result;// 检查结果


}
