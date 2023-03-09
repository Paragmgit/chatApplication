package com.example.ChatApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ChatApplication.model.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Integer>{
    
	@Query(value = "select * from chat_history_tbl where sender_id=:senderId and status_id=:1",nativeQuery = true)
	List<ChatHistory> getChatByUserId(int senderId);

	@Query(value = "select * from chat_history_tbl where (sender_id=:user1 and receiver_id=:user2) or (sender_id=:user2 and receiver_id=:user1) and status_id=:1",nativeQuery = true)
	List<ChatHistory> getConversation(int user1, int user2);
	
	@Query(value = "delete from chat_history_tbl where chat_id =:chatId and status_id =:2",countQuery = "SELECT count(*) FROM chat_history_tbl",nativeQuery = true)
	List<ChatHistory>deleteByChatId(int chatId);
	
}
