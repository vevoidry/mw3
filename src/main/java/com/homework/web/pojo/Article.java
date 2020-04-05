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

//文章
@Data
@Entity
@Table(name = "article")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;// 主键

	@Column(name = "title")
	String title;// 标题

	@Column(name = "content")
	String content;// 内容

	@Column(name = "create_time")
	Date create_time;// 创建时间

	@Column(name = "category")
	Integer category;// 分类，0为无分类，1为科普，2为公告
}
