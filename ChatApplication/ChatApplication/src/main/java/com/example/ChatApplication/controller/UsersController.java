package com.example.ChatApplication.controller;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ChatApplication.dao.StatusRepository;
import com.example.ChatApplication.dao.UsersRepository;
import com.example.ChatApplication.model.Status;
import com.example.ChatApplication.model.Users;
import com.example.ChatApplication.service.UsersService;
import com.example.ChatApplication.utils.CommonUtils;

import jakarta.annotation.Nullable;

@RestController
@RequestMapping(value ="/api/v1/users")
public class UsersController {

	@Autowired
    UsersService usersService;
	
	@Autowired
    StatusRepository statusRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	@PostMapping(value = "/create-users")
	public ResponseEntity<String> createUser(@RequestBody String userData){
		JSONObject isValid = validateUserRequest(userData);
		Users user = null;
		if(isValid.isEmpty()) {
			user = setUser(userData);
		    usersService.saveUser(user);
		}else {
			return new ResponseEntity<String>(isValid.toString(),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("saved",HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/get-users")
	public ResponseEntity<String> getUsers(@Nullable @RequestParam String userId){
		JSONArray userArr = usersService.getUsers(userId);
		return new ResponseEntity<String>(userArr.toString(),HttpStatus.OK);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody String requestData){
		JSONObject requestJson = new JSONObject(requestData);
		JSONObject isValidLogin = validateLogin(requestJson);
		if(isValidLogin.isEmpty()) {
			String username = requestJson.getString("username");
			String password = requestJson.getString("password");
			JSONObject responseObj = usersService.login(username,password);
			if(responseObj.has("errorMessage")) {
				return new ResponseEntity<String>(responseObj.toString(),HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<String>(responseObj.toString(),HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<String>(isValidLogin.toString(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	private JSONObject validateLogin(JSONObject requestJson) {
		JSONObject errorList = new JSONObject();
		if(!requestJson.has("username")) {
			errorList.put("username","missing parameter");
		}
		if(!requestJson.has("password")) {
			errorList.put("password","missing paramert");
		}
		return errorList;
	}
	
	@PutMapping(value = "/update-user/{userId}")
	public ResponseEntity<String> updatedUser(@PathVariable String userId,@RequestBody String requestData){
		JSONObject isRequestValid = validateUserRequest(requestData);
        Users user = null;

        if(isRequestValid.isEmpty()) {
            user = setUser(requestData);
            JSONObject responseObj = usersService.updateUser(user, userId);
            if(responseObj.has("errorMessage")) {
                return new ResponseEntity<String>(responseObj.toString(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>(isRequestValid.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("user updated", HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete-users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId){
		usersService.deleteUserByUserId(userId);
		return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
	}

	private JSONObject validateUserRequest(String userData) {
		JSONObject userJson = new JSONObject(userData);
		JSONObject errorList = new JSONObject();
		if(userJson.has("username")) {
			String username = userJson.getString("username");
			List<Users> userList = usersRepository.findByUsername(username);
			if(userList.size()>0) {
				errorList.put("username","this username already exits");
				return errorList;
			}
		}else {
			errorList.put("username","Missing parameter");
		}
		
		if(userJson.has("firstName")) {
			String firstName= userJson.getString("firstName");
		}else {
			errorList.put("firstName", "Missing parameter");
		}
		
		if(userJson.has("email")) {
			String email = userJson.getString("email");
			if(!CommonUtils.isValidEmail(email)) {
				errorList.put("email", "Please enter valid email");
			}
		}else {
			errorList.put("email","Missing parameter");
		}
		
		if(userJson.has("password")) {
			String password = userJson.getString("password");
			if(!CommonUtils.isValidPassword(password)) {
				errorList.put("password", "Please enter valid password");
			}
		}else {
			errorList.put("password","Missing parameter");
		}
		
		if(userJson.has("phoneNumber")) {
			String phoneNumber = userJson.getString("phoneNumber");
			if(!CommonUtils.isValidPhoneNumber(phoneNumber)) {
				errorList.put("phoneNumber", "Please enter valid phonenumber");
			}
		}else {
			errorList.put("phoneNumber","Missing parameter");
		}
		
		return errorList;
	}

	private Users setUser(String userData) {
		Users user = new Users();
		JSONObject json = new JSONObject(userData);
		
		user.setEmail(json.getString("email"));
        user.setPassword(json.getString("password"));
        user.setFirstName(json.getString("firstName"));
        user.setUsername(json.getString("username"));
        user.setPhoneNumber(json.getString("phoneNumber"));
		
        if(json.has("age")) {
            user.setAge(json.getInt("age"));
        }

        if(json.has("lastName")){
            user.setLastName(json.getString("lastName"));
        }
        if(json.has("gender")){
            user.setGender(json.getString("gender"));
        }
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        user.setCreatedDate(createdTime);

        Status status = statusRepository.findById(1).get();
        user.setStatusId(status);
        
        return user;
	}
}
