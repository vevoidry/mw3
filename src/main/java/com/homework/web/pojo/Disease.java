package com.homework.web.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//疾病
@Data
@Entity
@Table(name = "disease")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Disease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "name")
	String name;// 疾病（分类）名

	@Column(name = "parent_id")
	Integer parent_id;// 疾病父分类的id，初级分类的父分类为0

	@Column(name = "rank")
	Integer rank;// 疾病等级，1为初级分类，2为二级分类，3为疾病

}
