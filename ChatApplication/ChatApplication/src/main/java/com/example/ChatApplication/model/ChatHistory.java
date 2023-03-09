package com.example.ChatApplication.model;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "chat_history")
public class ChatHistory {
	
	@Id
	@Column(name = "chat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int chatId;
	
	@JoinColumn(name = "sender_user_id")
	@ManyToOne
	private Users sender;
	
	@JoinColumn(name = "receiver_user_id")
	@ManyToOne
	private Users receiver;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "create_date")
	private Timestamp createDate;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
    private Status statusId;

}
