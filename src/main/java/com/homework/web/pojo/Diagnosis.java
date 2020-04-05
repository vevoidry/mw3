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

//诊疗单
@Data
@Entity
@Table(name = "diagnosis")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Diagnosis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "appointment_id")
	Integer appointment_id;// 所属挂号单的id

	@Column(name = "disease_id")
	Integer disease_id;// 所属疾病id

	@Column(name = "remark")
	String remark;// 备注

	@Column(name = "time_day") // 用于统计图
	Date time_day;// 日期
	
	@Column(name = "gender")//用于统计图
	Boolean gender;// 性别（true为男，false为女）
}
