package com.homework.web.service;

import java.util.List;

import com.homework.web.pojo.User;

public interface UserService {
	User selectByUsername(String username);
//	
	User insert(User user);
//	
	User selectById(Integer id);
//	
	List<User> selectByRole(String role);
//	
	void updateRoleById(Integer id, String role);
	
	User update(User user);
//	
//	User selectByDepartment_idIs_leader(Integer department_id, Boolean is_leader);
//	
//	void updateIs_leaderById(Integer id, Boolean is_leader);
}
