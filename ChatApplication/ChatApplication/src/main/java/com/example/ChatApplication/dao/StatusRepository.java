package com.example.ChatApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ChatApplication.model.Status;

public interface StatusRepository extends JpaRepository<Status,Integer>{

}
