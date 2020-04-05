package com.homework.web.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//检查项目
@Data
@Entity
@Table(name = "check_project")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Check_project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "name")
	String name;// 检查项目名

	@Column(name = "check_department_id")
	Integer check_department_id;// 所属检查科室

}
