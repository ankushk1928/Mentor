package com.talentshare.mentor.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ErrorDetails {

	private int status;
	private String message;
	private String errors;
	private LocalDateTime timestamp;
	private String path;


}
