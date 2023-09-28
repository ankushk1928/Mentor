package com.talentshare.mentor.dto;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ValidationError {

	private int status;
	private String message;
	private Map<String, String> errors;
	private LocalDateTime timestamp;
	private String path;
}
