package com.example.ChatApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ChatApplication.dao.StatusRepository;
import com.example.ChatApplication.model.Status;

@Service
public class StatusService {
	
	@Autowired
	private StatusRepository statusRepository;

	public int saveStatus(Status status) {
		Status statusObj = statusRepository.save(status);
		return statusObj.getStatusId();
	}

}
