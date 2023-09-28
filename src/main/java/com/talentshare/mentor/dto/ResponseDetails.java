package com.talentshare.mentor.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include. NON_NULL)
public class ResponseDetails {
	
	private int status;
	private String message;
	private String errors;
	private LocalDateTime timestamp;
	private String path;
	private Object data;

}
