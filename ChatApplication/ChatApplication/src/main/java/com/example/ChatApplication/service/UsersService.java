package com.example.ChatApplication.service;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ChatApplication.dao.UsersRepository;
import com.example.ChatApplication.model.Users;

@Service
public class UsersService {
	
	@Autowired
	UsersRepository usersRepository;

	public int saveUser(Users userData) {
		Users userObj = usersRepository.save(userData);
		return userObj.getUserId();
	}

	public JSONArray getUsers(String userId) {
		JSONArray response = new JSONArray();
		if(null != userId) {
			List<Users>usersList = usersRepository.getUserByUserId(Integer.valueOf(userId));
			for(Users user:usersList) {
				JSONObject userObj = createResponse(user);
				response.put(userObj);
			}
		}else {
			List<Users> userList = usersRepository.getAllUsers();
			for(Users user: userList) {
				JSONObject userObj = createResponse(user);
				response.put(userObj);
			}
		}
		return response;
	}

	private JSONObject createResponse(Users user) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userId",user.getUserId());
		jsonObj.put("username",user.getUsername());
		jsonObj.put("firstName",user.getFirstName());
		jsonObj.put("lastName",user.getLastName());
		jsonObj.put("age",user.getAge());
		jsonObj.put("email",user.getEmail());
		jsonObj.put("phoneNumber",user.getPhoneNumber());
		jsonObj.put("gender",user.getGender());
		jsonObj.put("createdDate",user.getCreatedDate());
		
		return jsonObj;
	}

	public void deleteUserByUserId(int userId) {
		usersRepository.deleteUserByUserId(userId);
		
	}

	public JSONObject login(String username, String password) {
		 JSONObject response = new JSONObject();
	        List<Users> user = usersRepository.findByUsername(username);
	        if(user.isEmpty()) {
	            response.put("errorMessage", "username doesn't exist");
	        } else {
	            Users userObj = user.get(0);
	            if(password.equals(userObj.getPassword())) {
	                response = createResponse(userObj);
	            }else {
	                response.put("errorMessage" , "password is not valid");
	            }
	        }
	        return response;
	}

	public JSONObject updateUser(Users newUser, String userId) {
		 List<Users> usersList = usersRepository.getUserByUserId(Integer.valueOf(userId));
	        JSONObject obj = new JSONObject();
	        if(!usersList.isEmpty()) {
	            Users oldUser = usersList.get(0);
	            newUser.setUserId(oldUser.getUserId());
	            newUser.setCreatedDate(oldUser.getCreatedDate());
	            newUser.setPassword(oldUser.getPassword());
	            Timestamp updatedTime = new Timestamp(System.currentTimeMillis());
	            newUser.setUpdatedDate(updatedTime);
	            usersRepository.save(newUser);
	        } else {
	            obj.put("errorMessage" , "User doesn't exist");
	        }
	        return obj;
	    }

}
