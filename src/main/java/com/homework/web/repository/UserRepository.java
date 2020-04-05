package com.homework.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.homework.web.pojo.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "select * from user where username = :username", nativeQuery = true)
	User selectByUsername(String username);
//
	@Query(value = "select * from user where role = :role", nativeQuery = true)
	List<User> selectByRole(String role);
//
	@Modifying(clearAutomatically = true)
	@Query(value = "update user set role=:role where id=:id", nativeQuery = true)
	void updateRoleById(Integer id, String role);
//
//	@Query(value = "select * from user where department_id = :department_id and is_leader=:is_leader", nativeQuery = true)
//	User selectByDepartment_idIs_leader(Integer department_id, Boolean is_leader);
//
//	@Modifying(clearAutomatically = true)
//	@Query(value = "update user set is_leader=:is_leader where id=:id", nativeQuery = true)
//	void updateIs_leaderById(Integer id, Boolean is_leader);
}
