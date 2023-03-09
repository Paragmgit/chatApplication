package com.example.ChatApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.ChatApplication.model.Users;

import jakarta.transaction.Transactional;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	@Query(value = "select * from user_tbl where username = :username and status_id =1",nativeQuery = true)
	public List<Users> findByUsername(String username);
	
	@Query(value = "select * from user_tbl where user_id = :userId and status_id =1", nativeQuery = true)
	public List<Users>getUserByUserId(int userId);
	
	@Query(value = "select * from user_tbl where status_id=1",nativeQuery = true)
	public List<Users> getAllUsers();
	
	@Modifying
	@Transactional
	@Query(value = "update user_tbl set status_id = 2 where user_id = :userId", countQuery = "SELECT count(*) FROM user_tbl", nativeQuery = true)
	public void deleteUserByUserId(int userId);
}

