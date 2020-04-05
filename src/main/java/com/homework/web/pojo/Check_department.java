package com.homework.web.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//检查科室
@Data
@Entity
@Table(name = "check_department")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Check_department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "name")
	String name; // 科室名

	@Column(name = "leader_id")
	Integer leader_id;// 科室长的id

}
