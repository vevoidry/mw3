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

//药品
@Data
@Entity
@Table(name = "drug")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Drug {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "supplier_number")
	String supplier_number;// 供应商编号

	@Column(name = "name")
	String name;// 药物名

	@Column(name = "specification")
	String specification;// 规格

	@Column(name = "unit")
	String unit;// 单位

	@Column(name = "price")
	Double price;// 单价

	@Column(name = "quantity")
	Integer quantity;// 库存

	@Column(name = "start_time")
	Date start_time;// 生产日期

	@Column(name = "validity_time")
	Integer validity_time;// 有效期，单位为月

	@Column(name = "is_prescription")
	Boolean is_prescription;// 是否处方药，true为是，false为否

}
