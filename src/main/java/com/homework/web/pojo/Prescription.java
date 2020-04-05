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

//处方
@Data
@Entity
@Table(name = "prescription")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "appointment_id")
	Integer appointment_id;// 所属挂号单的id

	@Column(name = "drug_id")
	Integer drug_id;// 所属药物的id

	@Column(name = "quantity")
	Integer quantity;// 药物数量
	
	@Column(name = "time_day")//用于获取年月统计数据
	Date time_day;// 日期

}
