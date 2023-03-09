package com.example.ChatApplication.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ChatApplication.model.Status;
import com.example.ChatApplication.service.StatusService;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
	
	@Autowired
	private StatusService service;

	@PostMapping("/status")
	public ResponseEntity<String> createStatus(@RequestBody String statusData){
		Status status = setStatus(statusData);
		int statusId = service.saveStatus(status);
		return new ResponseEntity<String>("status sccussfully created"+statusId,HttpStatus.CREATED);
	}

	private Status setStatus(String statusData) {
		
		JSONObject json = new JSONObject(statusData);
		Status status = new Status();
		String statusName = json.getString("statusName");
		String statusDiscription = json.getString("statusDiscription");
		status.setStatusName(statusName);
		status.setStatusDiscription(statusDiscription);
		return status;
	}
}
