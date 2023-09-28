package com.talentshare.mentor.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.talentshare.mentor.constants.MessageConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mentorDetails")
public class Mentor {


	@Indexed(unique=true)
	//@NotNull(message = "Field must not be null")
	@Min(value = 1, message = MessageConstants.EMPLOYEE_ID_VALIDATION_MESSASGE)
	private int employeeId;

	@NotEmpty(message = "FirstName is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = MessageConstants.FIRSTNAME_VALIDATION_MESSASGE)
	private String firstName;

	@NotEmpty(message = "LastName is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = MessageConstants.LASTNAME_VALIDATION_MESSASGE)
	private String lastName;

	@Indexed(unique=true)
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = MessageConstants.EMAIL_VALIDATION_MESSAGE)
	private String email;

	private String password;

	@NotEmpty
	private String jobTitle;

	@NotEmpty
	private String location;

	@NotEmpty
	private String company;

	private Category category;

	@NotEmpty
	private List<String> skills;

	@NotEmpty
	private String bio;
	private LocalDateTime createdAt;
	private boolean isActive;
	private byte[] profileImage;

}


