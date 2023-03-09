package com.example.ChatApplication.controller;

import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ChatApplication.dao.StatusRepository;
import com.example.ChatApplication.dao.UsersRepository;
import com.example.ChatApplication.model.ChatHistory;
import com.example.ChatApplication.model.Status;
import com.example.ChatApplication.model.Users;
import com.example.ChatApplication.service.ChatHistoryService;


@RestController
@RequestMapping(value = "/api/v1/chat")
public class ChatHistoryController {

	@Autowired
	ChatHistoryService chatHistoryService;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
	@PostMapping(value = "/send-message")
	public ResponseEntity<String> saveMessage(@RequestBody String requestData){
		JSONObject requestObj = new JSONObject(requestData);
		JSONObject errorList = validateRequest(requestObj);
		if(errorList.isEmpty()) {
			ChatHistory chat = setChatHistory(requestObj);
			int chatId = chatHistoryService.saveMessage(chat);
			return new ResponseEntity<String>("message sent"+chatId,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>(errorList.toString(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get-chat")
	public ResponseEntity<String> getChatByUserId(@RequestParam int senderId){
		JSONObject response = chatHistoryService.getChatByUserId(senderId);
		return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
	}
	
	@GetMapping(value = "/get-conversation")
	public ResponseEntity<String> getCoversationBetweenTwoUser(@RequestParam int user1,@RequestParam int user2){
		JSONObject response = chatHistoryService.getConversation(user1,user2);
		return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete-chat")
	public void deleteChat(@PathVariable int chatId) {
		chatHistoryService.deleteByChatId(chatId);
	}

	private ChatHistory setChatHistory(JSONObject requestObj) {
		ChatHistory chat = new ChatHistory();
		String message = requestObj.getString("message");
		int senderId = requestObj.getInt("sender");
		int receiverId = requestObj.getInt("receiver");
		Users senderUserObj = usersRepository.findById(senderId).get();
		Users receiverUserObj = usersRepository.findById(receiverId).get();

		chat.setReceiver(receiverUserObj);
		chat.setSender(senderUserObj);
		chat.setMessage(message);
		Status status = statusRepository.findById(1).get();
		chat.setStatusId(status);

		Timestamp createdTime = new Timestamp(System.currentTimeMillis());
		chat.setCreateDate(createdTime);

		return chat;

	}

	private JSONObject validateRequest(JSONObject requestObj) {
		JSONObject errorObj = new JSONObject();
		if(!requestObj.has("sender")) {
			errorObj.put("sender","missing parameter");
		}
		if(!requestObj.has("receiver")) {
			errorObj.put("reciver","missing parameter");
		}
		if(requestObj.has("message")) {
			String message = requestObj.getString("message");
			if(message.isBlank() || message.isEmpty()) {
				errorObj.put("message","message body can't be empty");
			}
		}else {
			errorObj.put("message","missing parameter");
		}
		return errorObj;
	}
	
}
