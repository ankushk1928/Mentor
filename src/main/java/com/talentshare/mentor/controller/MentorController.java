package com.talentshare.mentor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentshare.mentor.constants.Constants;
import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.exception.ResourceNotFoundException;
import com.talentshare.mentor.model.Mentor;
import com.talentshare.mentor.service.MentorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(Constants.BASE_PATH)
@Slf4j
public class MentorController {

	@Autowired
	private MentorService mentorService;

	@Operation(
			summary="Checks if mentors credentials are valid",
			description="Validates mentors userName and password",
			responses= {
					@ApiResponse(
							description = "Valid User",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Unauthorised User",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@GetMapping(Constants.LOGIN_MENTOR)
	public ResponseEntity<ResponseDetails> logInMentor(@RequestParam String email, @RequestParam String password,
			HttpServletRequest httpServletRequest){
		log.info("Attempting to verify login details for username: {}", email);
		return new ResponseEntity<>(mentorService.verifyLogInDetails(email, password, httpServletRequest),HttpStatus.OK);

	}


	@Operation(
			summary="Retrieves list of mentors",
			description="Retrives list of menotrs using skills",
			responses= {
					@ApiResponse(
							description = "Valid User",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Unauthorised User",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@GetMapping(Constants.SEARCH_MENTOR)
	public ResponseEntity<ResponseDetails> getMentorsListBySkill(@RequestParam List<String> skills,
			HttpServletRequest httpServletRequest){
		log.info("Searching for mentors with skills: {}", skills);
		return new ResponseEntity<>(mentorService.findMentorBySkills(skills,httpServletRequest),HttpStatus.OK);
	}


	@Operation(
			summary="Retrieve mentor",
			description="Retrives mentor by employeeId",
			responses= {
					@ApiResponse(
							description = "Valid User",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Unauthorised User",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@GetMapping(value=Constants.GET_MENTOR)
	public ResponseEntity<ResponseDetails> getMentorById(@PathVariable int empid,
			HttpServletRequest httpServletRequest){

		log.info("Searching for mentor by employee Id"+empid);

		return new ResponseEntity<>(mentorService.getMentorById(empid, httpServletRequest),HttpStatus.CREATED);
	}


	@Operation(
			summary="Retrieves list of all mentors",
			description="Retrives list of all mentors",
			responses= {
					@ApiResponse(
							description = "Successful",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Bad Request",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})

	@GetMapping("/getAllMentors")
	public ResponseEntity<ResponseDetails> getAllMentors(HttpServletRequest httpServletRequest){

		log.info("Searching for all mentors");

		return new ResponseEntity<>(mentorService.getAllMentors(httpServletRequest),HttpStatus.FOUND);
	}

	@Operation(
			summary="Deletes Mentor",
			description="Deletes Mentor",
			responses= {
					@ApiResponse(
							description = "Successful",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Bad Request. Mentor Not Found",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@DeleteMapping(value=Constants.DELETE_MENTOR)
		public ResponseEntity<ResponseDetails> deleteMentor(@PathVariable int empid,HttpServletRequest httpServletRequest) {
		log.info("Searching for mentor");

		return new ResponseEntity<>(mentorService.deleteMentor(empid,httpServletRequest),HttpStatus.OK);

	}


	@Operation(summary = "Mentor Sign up",
			responses= {
					@ApiResponse(
							responseCode = "201",
							description = "User successfully signed up"),
					@ApiResponse(
							responseCode = "400",
							description = "Bad request. Invalid input data"),
					@ApiResponse(
							responseCode = "409",
							description = "Conflict. User already exists"),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error") })


	@PostMapping(value = Constants.REGISTER_MENTOR)
	public ResponseEntity<ResponseDetails> createMentor(@Valid @RequestBody Mentor mentor, HttpServletRequest request) {

		log.info("Register Mentor API Called!");
		return new ResponseEntity<>(mentorService.registerMentor(mentor, request),HttpStatus.CREATED);

	}


	@Operation(summary = "Update Mentor",
			responses= {
					@ApiResponse(
							responseCode = "200",
							description = "Update successful"),
					@ApiResponse(
							responseCode = "400",
							description = "Bad request. Invalid input data"),
					@ApiResponse(
							responseCode = "404",
							description = "Resource not found"),
					@ApiResponse(
							responseCode = "409",
							description = "Conflict. Update cannot be completed due to conflict"),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error")})
	@PutMapping(value = Constants.UPDATE_MENTOR)
	public  ResponseEntity<ResponseDetails> updateMentor(@PathVariable int empid, @RequestBody Mentor mentor, HttpServletRequest request) throws ResourceNotFoundException {

		log.info("Update Mentor API Called!");

		 return new ResponseEntity<>(mentorService.updateMentor(empid, mentor, request),HttpStatus.OK);

	}


}


