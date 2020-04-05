package com.homework.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.web.pojo.User;
import com.homework.web.repository.UserRepository;
import com.homework.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User selectByUsername(String username) {
		return userRepository.selectByUsername(username);
	}

//
	@Override
	public User insert(User user) {
		Date date = new Date();
		user.setNickname(date.getTime() + "");
		user.setGender(true);
		user.setImage("x.png");
		user.setBirthday(date);
		user.setPhone_number("1234567890");
		user.setRole("patient");
		user.setDepartment_id(0);
		user.setHiredate(date);
		return userRepository.save(user);
	}
//
	@Override
	public User selectById(Integer id) {
		return userRepository.findById(id).get();
	}
//
	@Override
	public List<User> selectByRole(String role) {
		return userRepository.selectByRole(role);
	}
//
	@Override
	public void updateRoleById(Integer id, String role) {
		userRepository.updateRoleById(id, role);
	}
//
//	@Override
//	public User selectByDepartment_idIs_leader(Integer department_id, Boolean is_leader) {
//		return userRepository.selectByDepartment_idIs_leader(department_id, is_leader);
//	}
//
//	@Override
//	public void updateIs_leaderById(Integer id, Boolean is_leader) {
//		userRepository.updateIs_leaderById(id, is_leader);
//	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

}
