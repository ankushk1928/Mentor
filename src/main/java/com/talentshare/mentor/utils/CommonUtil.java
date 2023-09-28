package com.talentshare.mentor.utils;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.talentshare.mentor.dto.ErrorDetails;
import com.talentshare.mentor.dto.ResponseDetails;

@Component
@Configuration
public class CommonUtil {


	 BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	public String encryptPassword(String password) {
		//return BCrypt.hashpw(password, BCrypt.gensalt());
		return bcrypt.encode(password);
		}
	 
	 public boolean matchPassword(String rawPassword, String encodedPassword) {
		     //return BCrypt.checkpw(rawPassword, encodedPassword);
	        return bcrypt.matches(rawPassword, encodedPassword);
	    }

	
	public ResponseDetails prepareResponse(String message, Integer value, String path, Object data) {
			
		ResponseDetails response = new ResponseDetails();
		response.setStatus(value);
		response.setMessage(message);
		response.setPath(path);
		response.setTimestamp(LocalDateTime.now());
		response.setData(data);
		return response;
			
		}
	
	
	public ErrorDetails errorResponse(int value, String message, String error, String path) {
		
		ErrorDetails response; response = new ErrorDetails();
		response.setStatus(value);
		response.setMessage(message);
		response.setErrors(error);
		response.setTimestamp(LocalDateTime.now());
		response.setPath(path);
		return response;
			
		}
	
	
}
