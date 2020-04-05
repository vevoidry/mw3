//package com.homework.web.pojo;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import lombok.Data;
//
////医生科室
//@Data
//@Entity
//@Table(name = "department")
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
//public class Department {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	Integer id;// 主键
//
//	@Column(name = "name")
//	String name; // 科室名
//
//	@Column(name = "phone_number")
//	String phone_number;// 科室电话号码
//
//}
