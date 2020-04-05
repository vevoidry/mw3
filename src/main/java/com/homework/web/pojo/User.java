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

//用户
//lombok的注解，自动为所有属性添加setter，getter，hashcode，equals，toString，无参构造器
@Data
//以下注解用来对应数据库中的一个表
@Entity // 说明该类是用来对应数据库中的一个表的
@Table(name = "user") // 说明该类所对应的表的表名
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class User {
	@Id // 设置该属性为主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键类型
	@Column(name = "id") // 设置该属性对应表中的字段名
	Integer id;// 主键

	@Column(name = "username")
	String username;// 用户名（用于登录）

	@Column(name = "password")
	String password; // 密码（用于登录）

	@Column(name = "nickname")
	String nickname;// 昵称（真实姓名，用于显示）

	@Column(name = "gender")
	Boolean gender;// 性别（true为男，false为女）

	@Column(name = "image")
	String image;// 头像

	@Column(name = "birthday")
	Date birthday;// 生日

	@Column(name = "phone_number")
	String phone_number;// 电话号码

	@Column(name = "role")
	String role;// 角色（普通用户为patient，医护人员为worker，管理员为admin）

	@Column(name = "hiredate")
	Date hiredate;// 入职日期（只有医护人员需要设置该选项）

	@Column(name = "department_id")
	Integer department_id; // 科室（病人和管理员为0，医护人员则为科室的id）

}
