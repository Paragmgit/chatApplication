package com.example.ChatApplication.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ChatApplication.dao.ChatHistoryRepository;
import com.example.ChatApplication.model.ChatHistory;

@Service
public class ChatHistoryService {
	
	@Autowired
	ChatHistoryRepository chatHistoryRepository;

	public int saveMessage(ChatHistory chat) {
		ChatHistory chatHistory = chatHistoryRepository.save(chat);
		return chatHistory.getChatId();
	}

	public JSONObject getChatByUserId(int senderId) {
		List<ChatHistory>chatList = chatHistoryRepository.getChatByUserId(senderId);
		JSONObject response = new JSONObject();
		if(!chatList.isEmpty()) {
			response.put("senderId",chatList.get(0).getSender().getUserId());
			response.put("senderName",chatList.get(0).getSender().getFirstName());
		}
		JSONArray recivers = new JSONArray();
		for(ChatHistory chats:chatList) {
			JSONObject reciverObject = new JSONObject();
			reciverObject.put("reciverId",chats.getReceiver().getUserId());
			reciverObject.put("reciverName",chats.getReceiver().getFirstName());
			reciverObject.put("message",chats.getMessage());
			recivers.put(reciverObject);
		}
		response.put("recivers",recivers);
		return response;
	}

	public JSONObject getConversation(int user1, int user2) {
		JSONObject response = new JSONObject();
		JSONArray conversations = new JSONArray();
		List<ChatHistory>chatList = chatHistoryRepository.getConversation(user1,user2);
		for(ChatHistory chats:chatList) {
			JSONObject messageObj = new JSONObject();
			messageObj.put("chatId",chats.getChatId());
			messageObj.put("timestamp",chats.getCreateDate());
			messageObj.put("senderName",chats.getSender().getFirstName());
			messageObj.put("message",chats.getMessage());
			conversations.put(messageObj);
		}
		response.put("conversations",conversations);
		return response;
	}

	public void deleteByChatId(int chatId) {
		chatHistoryRepository.deleteByChatId(chatId);	
	}

}
